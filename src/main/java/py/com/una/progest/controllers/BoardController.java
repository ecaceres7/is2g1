package py.com.una.progest.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import py.com.una.progest.models.*;
import py.com.una.progest.payload.request.BoardDataRequest;
import py.com.una.progest.payload.request.NewBoardRequest;
import py.com.una.progest.payload.request.NewTaskRequest;
import py.com.una.progest.payload.response.BoardDataResponse;
import py.com.una.progest.payload.response.MessageResponse;
import py.com.una.progest.repository.BoardRepository;
import py.com.una.progest.repository.ListColumnRepository;
import py.com.una.progest.repository.TaskRepository;
import py.com.una.progest.repository.UserRepository;
import py.com.una.progest.service.TaskService;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final ListColumnRepository columnRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Autowired
    public BoardController(BoardRepository boardRepository, ListColumnRepository columnRepository, UserRepository userRepository, TaskRepository taskRepository, TaskService taskService) {
        this.boardRepository = boardRepository;
        this.columnRepository = columnRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createBoard(@Valid @RequestBody NewBoardRequest boardRequest) {
        Date date = new Date();

        Board newBoard = new Board(null, boardRequest.getName(), date, userRepository.findByUsername(getUsername()).get());
        newBoard = boardRepository.save(newBoard);

        Set<ListColumn> columnSet = new HashSet<>();
        ListColumn columnNew = new ListColumn(null, "New", date, newBoard);
        ListColumn columnInProgress = new ListColumn(null, "In Progress", date, newBoard);
        ListColumn columnDone = new ListColumn(null, "Done", date, newBoard);

        columnSet.add(columnNew);
        columnSet.add(columnInProgress);
        columnSet.add(columnDone);

        columnRepository.saveAll(columnSet);

        return ResponseEntity.ok(new MessageResponse("Board created successfully!"));
    }

    @PostMapping("/task/new")
    public ResponseEntity<?> createTask(@Valid @RequestBody NewTaskRequest taskRequest) {
        ListColumn column = columnRepository.findByBoardIdAndName(taskRequest.getBoard(), "New").get();
        if (!taskRepository.existsByTitleAndListColumn(taskRequest.getTitle(), column)) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            // Suma dos semanas a la fecha actual (duracion del spring) por defecto
            calendar.add(Calendar.WEEK_OF_YEAR, 2);

            Task task = new Task(null, taskRequest.getTitle(), taskRequest.getDescription() != null ? taskRequest.getDescription() : "", date, calendar.getTime(),
                    column, null);
            taskRepository.save(task);

            return ResponseEntity.ok(new MessageResponse("Task created successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Existing task"));
        }
    }

    @GetMapping("/getColumns/{boardId}")
    public ResponseEntity<?> getColumns(@PathVariable Long boardId) {
        if (boardRepository.existsById(boardId)) {
            BoardDataResponse boardResponse = new BoardDataResponse();
            Set<ListColumn> columns = columnRepository.findByBoardId(boardId).get();
            columns.stream()
                    .map(ListColumn::getId)
                    .forEach(columnId -> {
                        Set<Task> tasks = taskRepository.findByListColumnId(columnId).get();
                        if (tasks != null && !tasks.isEmpty()) {
                            boardResponse.addBoardData(tasks.stream()
                                    .map(task -> new BoardData(task.getId(), task.getTitle(), task.getDescription(), columnId))
                                    .collect(Collectors.toList()));
                        }
                    });
            return ResponseEntity.ok(boardResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Board not exist"));
        }
    }

    @PatchMapping("/updateBoard")
    public ResponseEntity<?> updateBoard(@Valid @RequestBody BoardDataRequest boardRequest) {
        List<BoardData> boardData = boardRequest.getBoardDataReq();
        boardData.stream().forEach(board -> taskService.updateTaskListColumn(board.getId(), board.getColumn()));

        return ResponseEntity.ok("Board is updated!!");
    }


    private static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }

}

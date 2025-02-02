package com.exadel.team2.sandbox.controller;


import com.exadel.team2.sandbox.service.StatusHistoryService;
import com.exadel.team2.sandbox.web.statushistory.CreateStatusHistoryDTO;
import com.exadel.team2.sandbox.web.statushistory.ResponseStatusHistoryDTO;
import com.exadel.team2.sandbox.web.statushistory.UpdateStatusHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status/history")
@RequiredArgsConstructor
public class StatusHistoryController {

    private final StatusHistoryService historyService;

    @GetMapping("/{id}")
    public ResponseStatusHistoryDTO findStatusHistoryById(@PathVariable("id") Long id) {
        return historyService.findById(id);
    }

    @GetMapping("/all")
    public Page<ResponseStatusHistoryDTO> findAllStatusHistoryPageable(
            @RequestParam(defaultValue = "", value = "search") String search,
            @RequestParam(defaultValue = "0", name = "page") Integer page,
            @RequestParam(defaultValue = "9", name = "itemsPerPage") Integer itemsPerPage) {
        return historyService.findAllPageable(PageRequest.of(page, itemsPerPage), search);
    }

    @PostMapping
    public ResponseStatusHistoryDTO saveStatusHistory(@Validated @RequestBody CreateStatusHistoryDTO statusHistoryDTO) {
        return historyService.save(statusHistoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseStatusHistoryDTO updateStatusHistory(@PathVariable("id") Long id,
                                                        @Validated @RequestBody UpdateStatusHistoryDTO updateStatusHistoryDTO) {
        return historyService.update(id, updateStatusHistoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStatusHistoryById(@PathVariable("id") Long id) {
        if (historyService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        historyService.deleteById(id);
        return ResponseEntity.ok().build();

    }


}

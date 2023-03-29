package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.PaginationResponseRest;
import peaksoft.dto.response.PaginationResponseStopList;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopList")
public class StopListApi {
    private final StopListService stopListService;

    @Autowired
    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid StopListRequest stopListRequest) {
        return stopListService.saveStopList(stopListRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping
    public List<StopListResponse> getAll(){
        return stopListService.getAllStopLists();
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public StopListResponse getById(@PathVariable Long id){
        return stopListService.getStopListById(id);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid StopListRequest stopListRequest){
        return stopListService.updateStopList(id,stopListRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return stopListService.deleteStopListById(id);
    }
    @GetMapping("/paginationStopLust")
    public PaginationResponseStopList getStopListPagination(@RequestParam int page, @RequestParam int size) {
        return stopListService.getStopListPagination(page, size);

    }
}
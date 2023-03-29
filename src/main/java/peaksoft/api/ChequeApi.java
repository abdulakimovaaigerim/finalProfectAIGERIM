package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeOneDayWaiterRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.service.ChequeService;

import java.util.List;

@RestController
@RequestMapping("/api/cheques")
public class ChequeApi {
    private final ChequeService chequeService;

    @Autowired
    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping
    public SimpleResponse save(@RequestBody @Valid ChequeRequest chequeRequest) {
        return chequeService.saveCheque(chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping
    public List<ChequeResponse> getAll() {
        return chequeService.getAllCheques();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    @GetMapping("/{id}")
    public ChequeResponse getById(@PathVariable Long id) {
        return chequeService.getByIdCheque(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid ChequeRequest chequeRequest) {
        return chequeService.updateCheque(id, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return chequeService.deleteChequeById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("kq")
    public ChequeResponseWaiter summa(@RequestBody ChequeOneDayWaiterRequest request){
        return chequeService.getAverageSumma(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getRestId/{id}")
    public Double getByRest(@PathVariable Long id) {
        return chequeService.getAverageSumma(id);
    }
    @GetMapping("/paginationCheque")
    public PaginationResponseCheque getCategoryPagination(@RequestParam int page, @RequestParam int size){
        return chequeService.getChequePagination(page, size);

    }
}

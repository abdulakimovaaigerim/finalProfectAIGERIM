package peaksoft.service;

import peaksoft.dto.request.ChequeOneDayWaiterRequest;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.ChequeResponseWaiter;
import peaksoft.dto.response.PaginationResponseCheque;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface ChequeService {
    SimpleResponse saveCheque(ChequeRequest chequeRequest);
    List<ChequeResponse> getAllCheques();
    ChequeResponse getByIdCheque(Long id);
    SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest);
    SimpleResponse deleteChequeById(Long id);
    ChequeResponseWaiter getAverageSumma(ChequeOneDayWaiterRequest chequeOneDayWaiterRequest);
    public Double getAverageSumma(Long restaurantId);

    PaginationResponseCheque getChequePagination(int page, int size);

}

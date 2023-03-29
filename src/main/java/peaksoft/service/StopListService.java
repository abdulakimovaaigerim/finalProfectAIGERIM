package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.PaginationResponseStopList;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse saveStopList(StopListRequest stopListRequest);
    List<StopListResponse> getAllStopLists();
    StopListResponse getStopListById(Long id);
    SimpleResponse updateStopList(Long id, StopListRequest stopListRequest);
    SimpleResponse deleteStopListById(Long id);

    PaginationResponseStopList getStopListPagination(int page, int size);
}

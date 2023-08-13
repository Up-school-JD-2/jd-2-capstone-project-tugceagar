package io.upschool.capstoneProject.controller;

import io.upschool.capstoneProject.dto.BaseResponse;
import io.upschool.capstoneProject.dto.ticket.TicketSaveRequest;
import io.upschool.capstoneProject.dto.ticket.TicketSaveResponse;
import io.upschool.capstoneProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;


    @PostMapping
    public ResponseEntity<BaseResponse<TicketSaveResponse>> createTicket(@RequestBody TicketSaveRequest ticket)  {
        var ticketResponse = ticketService.save(ticket);
        var response = BaseResponse.<TicketSaveResponse>builder()
                .status(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(ticketResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<TicketSaveResponse>>> getAllTickets() {
        var tickets = ticketService.getAllTickets();
        BaseResponse<List<TicketSaveResponse>> response = BaseResponse.<List<TicketSaveResponse>>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(tickets)
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("{ticketNumber}")
    public ResponseEntity<BaseResponse<TicketSaveResponse>> getTicket(@PathVariable String ticketNumber) {
        TicketSaveResponse ticket = ticketService.findTicketByNumber(ticketNumber);
        BaseResponse<TicketSaveResponse> response = BaseResponse.<TicketSaveResponse>builder()
                .status(HttpStatus.OK.value())
                .isSuccess(true)
                .data(ticket)
                .build();
        return ResponseEntity.ok(response);
    }

//    @DeleteMapping("{ticketNumber}")
//    public String ticketCancel(@PathVariable("ticketNumber") String ticketNumber) {
//        ticketService.ticketCancel(ticketNumber);
//        return "Ticket is canceled.";
//    }
@DeleteMapping("/{ticketNumber}")
public ResponseEntity<BaseResponse<String>> ticketCancel(@PathVariable("ticketNumber") String ticketNumber) {
    ticketService.ticketCancel(ticketNumber);

    BaseResponse<String> response = BaseResponse.<String>builder()
            .status(HttpStatus.OK.value())
            .isSuccess(true)
            .data("Ticket is canceled.")
            .build();

    return ResponseEntity.ok(response);
}

}

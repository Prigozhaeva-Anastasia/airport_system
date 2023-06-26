package com.example.course_project.web;

import com.example.course_project.entity.*;
import com.example.course_project.service.TransferService;
import com.example.course_project.service.TransitFlightService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.course_project.constants.Constants.*;

@Controller
@RequestMapping(value = "/transfers")
public class TransferController {
    private TransferService transferService;
    private TransitFlightService transitFlightService;

    public TransferController(TransferService transferService, TransitFlightService transitFlightService) {
        this.transferService = transferService;
        this.transitFlightService = transitFlightService;
    }
    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String transfers(Model model, Long transitFlightId) {
        TransitFlight transitFlight = transitFlightService.findTransitFlightById(transitFlightId);
        List<Transfer> transfers = transitFlight.getTransferList();
        model.addAttribute(LIST_TRANSFERS, transfers);
        model.addAttribute(TRANSIT_FLIGHT, transitFlight);
        return "transfer-views/transfers";
    }
    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formTransfers(Model model, Long transitFlightId) {
        TransitFlight transitFlight = transitFlightService.findTransitFlightById(transitFlightId);
        model.addAttribute(TRANSIT_FLIGHT, transitFlight);
        model.addAttribute("direction", transitFlight.getArrivalCity() + "-" + transitFlight.getDepartureCity());
        model.addAttribute(TRANSFER, new Transfer());
        return "transfer-views/formCreate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid Transfer transfer, Long transitFlightId, BindingResult bindingResult) {
        TransitFlight transitFlightDB = transitFlightService.findTransitFlightById(transitFlightId);
        if (bindingResult.hasErrors()) return "transfer-views/formCreate";
        transfer.setTransitFlight(transitFlightDB);
        transferService.createOrUpdateTransfer(transfer);
        if (transitFlightDB.getCountOfTransfers() > transitFlightService.findTransitFlightById(transitFlightId).getTransferList().size()) return "redirect:/transfers/formCreate?transitFlightId=" + transitFlightDB.getId();
        else if (transitFlightDB.getCountOfTransfers() == transitFlightService.findTransitFlightById(transitFlightId).getTransferList().size())  return "redirect:/transitFlights/index";
        else {
            transitFlightDB.setCountOfTransfers(transitFlightService.findTransitFlightById(transitFlightId).getTransferList().size());
            transitFlightService.createOrUpdateTransitFlight(transitFlightDB);
            return "redirect:/transfers/index?transitFlightId=" + transitFlightDB.getId();
        }
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateTransfer(Model model, Long transferId) {
        Transfer transfer = transferService.findTransferById(transferId);
        model.addAttribute("direction", transfer.getTransitFlight().getArrivalCity() + "-" + transfer.getTransitFlight().getDepartureCity());
        model.addAttribute(TRANSFER, transfer);
        return "transfer-views/formUpdate";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid Transfer transfer, Long transitFlightId) {
        transfer.setTransitFlight(transitFlightService.findTransitFlightById(transitFlightId));
        transferService.createOrUpdateTransfer(transfer);
        return "redirect:/transfers/index?transitFlightId=" + transitFlightId;
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteTransfer(Long transferId, Long transitFlightId) {
        transferService.removeTransfer(transferId);
        return "redirect:/transfers/index?transitFlightId=" + transitFlightId;
    }
}

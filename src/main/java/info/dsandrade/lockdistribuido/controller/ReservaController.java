package info.dsandrade.lockdistribuido.controller;

import info.dsandrade.lockdistribuido.cmd.ReservaCmd;
import info.dsandrade.lockdistribuido.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservaController {
    @Autowired
    private ReservaService reservaService;

    @PostMapping("/lock")
    public String reservar(@RequestBody ReservaCmd cmd) {
        reservaService.reservar(cmd);
        return "ok";
    }

    @PostMapping("/release")
    public String release(@RequestBody ReservaCmd cmd) {
        reservaService.liberarReserva(cmd);
        return "ok";
    }
}

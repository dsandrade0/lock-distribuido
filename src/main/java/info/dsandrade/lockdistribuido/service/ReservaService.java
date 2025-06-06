package info.dsandrade.lockdistribuido.service;

import info.dsandrade.lockdistribuido.cmd.ReservaCmd;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    private final LockService lockService;

    public ReservaService(LockService lockService) {
        this.lockService = lockService;
    }

    public void reservar(ReservaCmd cmd) {
        try {
            lockService.acquireLock(cmd.idImovel(), cmd.idUsuario());
            //TODO codigo de realizacao da reserva
        } catch(IllegalStateException e) {
            throw new IllegalStateException("Não foi possível adiquirir o lock");
        }
    }

    public void liberarReserva(ReservaCmd cmd) {
        try {
            //TODO codigo para liberar a reserva
            lockService.releaseLock(cmd.idImovel(), cmd.idUsuario());
        } catch(IllegalStateException e) {
            throw new IllegalStateException("Não foi possivel liberar a reserva");
        }
    }
}

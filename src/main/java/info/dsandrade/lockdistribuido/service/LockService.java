package info.dsandrade.lockdistribuido.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LockService {
    private final RedisTemplate<String, String> redisTemplate;

    private static final RedisScript<Boolean> releaseScript = RedisScript.of(
            new ClassPathResource("redis/scripts/lock_release.lua"),
            Boolean.class
    );

    public LockService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String PREFIXO_LOCK = "LOCK-RESERVA::";
    private static final Duration DURACAO_LOCK = Duration.of(2, ChronoUnit.HOURS);

    public synchronized void acquireLock(String idImovel, String idUsuario) {
        Boolean realizouLock = redisTemplate
                    .opsForValue()
                    .setIfAbsent(PREFIXO_LOCK + idImovel, idUsuario, DURACAO_LOCK);

        if (Boolean.FALSE.equals(realizouLock)) {
            throw new RuntimeException("Recurso bloqueado");
        }
    }

    public synchronized void releaseLock(String idImovel, String idUsuario) {

        Boolean executou = redisTemplate.execute(
                releaseScript,
                List.of(PREFIXO_LOCK + idImovel),
                idUsuario);

        if (!executou) {
            throw new RuntimeException("Lock não pode ser removido.");
        }
    }
}

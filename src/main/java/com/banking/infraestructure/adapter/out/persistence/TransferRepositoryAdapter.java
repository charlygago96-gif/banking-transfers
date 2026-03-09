package com.banking.infraestructure.adapter.out.persistence;

import com.banking.domain.model.Transfer;
import com.banking.domain.port.out.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransferRepositoryAdapter implements TransferRepository {

    private final TransferJpaRepository jpaRepository;

    @Override
    public Transfer save(Transfer transfer) {
        TransferEntity entity = new TransferEntity(
                transfer.getId(),
                transfer.getSourceAccountId(),
                transfer.getTargetAccountId(),
                transfer.getAmount().amount(),
                transfer.getAmount().currency(),
                transfer.getStatus().name(),
                transfer.getCreatedAt()
        );
        jpaRepository.save(entity);
        return transfer;
    }
}
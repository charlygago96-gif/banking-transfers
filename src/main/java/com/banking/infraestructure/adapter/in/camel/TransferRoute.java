package com.banking.infrastructure.adapter.in.camel;

import com.banking.domain.model.Transfer;
import com.banking.domain.port.in.TransferUseCase;
import com.banking.infraestructure.adapter.in.camel.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferRoute extends RouteBuilder {

    private final TransferUseCase transferUseCase;

    @Override
    public void configure() {

        // Gestión de errores global para esta ruta
        onException(Exception.class)
                .handled(true)
                .setBody(simple("Error: ${exception.message}"))
                .log("ERROR en ruta de transferencia: ${exception.message}");

        // Ruta principal: recibe JSON → procesa → devuelve resultado
        from("direct:transfer")
                .routeId("transfer-route")
                .log("Recibida solicitud de transferencia: ${body}")
                .process(exchange -> {
                    TransferRequest req = exchange.getIn().getBody(TransferRequest.class);

                    Transfer result = transferUseCase.execute(
                            req.getSourceAccountId(),
                            req.getTargetAccountId(),
                            req.getAmount(),
                            req.getCurrency()
                    );

                    exchange.getIn().setBody(result);
                })
                .log("Transferencia procesada con ID: ${body.id}");
    }
}
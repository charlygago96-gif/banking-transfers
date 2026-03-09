package com.banking.infraestructure.adapter.in.camel;

import com.banking.domain.model.Transfer;
import com.banking.domain.port.in.TransferUseCase;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransferRoute extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(TransferRoute.class);

    private final TransferUseCase transferUseCase;

    public TransferRoute(TransferUseCase transferUseCase) {
        this.transferUseCase = transferUseCase;
    }

    @Override
    public void configure() {

        onException(Exception.class)
                .handled(true)
                .setBody(simple("Error: ${exception.message}"))
                .log("ERROR en ruta de transferencia: ${exception.message}");

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
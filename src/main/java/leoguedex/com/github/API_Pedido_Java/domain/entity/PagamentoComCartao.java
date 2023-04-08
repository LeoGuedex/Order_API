package leoguedex.com.github.API_Pedido_Java.domain.entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import leoguedex.com.github.API_Pedido_Java.domain.enums.EstadoPagamento;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 1L;

    private Integer numeroDeParcelas;

    public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

}

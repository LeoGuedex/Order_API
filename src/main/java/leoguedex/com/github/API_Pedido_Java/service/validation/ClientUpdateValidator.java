package leoguedex.com.github.API_Pedido_Java.service.validation;

import leoguedex.com.github.API_Pedido_Java.domain.repository.ClienteRepository;
import leoguedex.com.github.API_Pedido_Java.rest.dto.ClienteDTO;
import leoguedex.com.github.API_Pedido_Java.rest.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void initialize(ClientUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDTO clienteDto, ConstraintValidatorContext constraintValidatorContext) {
        Map<String, String> map = (Map<String, String>)
                httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        if (clienteRepository.findByEmail(clienteDto.getEmail()) != null){
            list.add(new FieldMessage("email", "E-mail já cadastrado"));
        }

        for (FieldMessage e: list){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}

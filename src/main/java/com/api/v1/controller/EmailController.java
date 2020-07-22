package com.api.v1.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import com.api.v1.model.EmailModel;
import com.api.v1.model.VeiculoModel;
import com.api.v1.repository.VeiculoRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

     @Autowired    
    private final VeiculoRepository repository;

    EmailController(final VeiculoRepository repository) {
        this.repository = repository;
    }

    
    @PostMapping("/send/{id}")
    public ResponseEntity<Object> sendEmail(@PathVariable(value = "id") final Integer id, @RequestBody EmailModel Email) {
        Map<String, String> response = new HashMap();
        
        if (Email.getEmail().isEmpty()) {
             response.put("status", "false");
             response.put("message", "Email não informado, não foi possivel enviar email!");
            
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
        

        final Optional<VeiculoModel> veiculo = repository.findById(id);

        if (!veiculo.isPresent()) {
             response.put("status", "false");
             response.put("message", "Veiculo não foi encontrado!");
            
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }
    
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            String html = 
              "<div  style='background: #000; color: #fff'>" +
                "<div>"+
                    "<img src='" + veiculo.get().getImagem() + "' style='width: 100%'>" +
                "</div>" +
                "<div  style='padding: 20px 20px;'>" + 
                    "<div style='display: flex; align-items: center;'>" +
                        "<div>" + 
                            "<img src='data:image/png;base64,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' width='50'>" +
                        "</div>" + 
                        "<div style='padding-left: 10px;'>" + 
                        "<h1 style='font-size: 1rem; font-weight: bold;'>SearchBus</h1>" +
                    "</div>" +
                    "</div>" +
                    "<p style='padding: 10px 10px;'><b>Marca:</b>" + veiculo.get().getMarca() + "</p>"  +
                    "<p style='padding: 10px 10px;'><b>Eixo:</b> " + veiculo.get().getAno() + "</p>" +
                    "<p style='padding: 10px 10px;'><b>Ano:</b> " + veiculo.get().getAno() + "</p>" +
                    "<p style='padding: 10px 10px;'><b>Geração:</b> " + veiculo.get().getGeracao() + "</p>" +
                    "<p style='padding: 10px 10px;'><b>Categoria:</b> " + veiculo.get().getCategoria() + "</p>" +
                    "<p style='padding: 10px 10px;'><b>Visualizações:</b>" + veiculo.get().getVisualizar() + "</p>" +
                    "<p style='padding: 10px 10px;'><b>Descrição:</b> " + veiculo.get().getDescricao() + " </p>" +
                    "<div style='margin-top: 20px; margin-bottom: 20px; margin-left: 10px;'>" +
                        "<a style='text-decoration: none; color: #fff; border: 1px solid #fff; padding: 10px 10px;' href='https://searchbus.herokuapp.com/veiculo/" + veiculo.get().getId() + "'>Saiba Mais</a>" +
                    "</div>" +
                "</div>" +
            "</div>";

            MimeMessageHelper helper = new MimeMessageHelper(mail);
            
            helper.setTo(Email.getEmail());
            helper.setSubject("SearchBus - " + veiculo.get().getNome_comercial() +" | "+ veiculo.get().getMarca());
            helper.setText(html, true);
            mailSender.send(mail);

            response.put("status", "true");
            response.put("message", "Email enviado para "+ Email.getEmail() + " com sucesso !");

            return new ResponseEntity<> (response,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            
            response.put("status", "false");
            response.put("message", "Não foi possivel enviar email!");

            return new ResponseEntity<> (response,HttpStatus.OK);
        }
    }
}
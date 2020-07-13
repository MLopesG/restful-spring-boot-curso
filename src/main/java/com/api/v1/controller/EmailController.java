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
                            "<img src='https://searchbusdev.000webhostapp.com/logo.png' width='50'>" +
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
                        "<a style='text-decoration: none; color: #fff; border: 1px solid #fff; padding: 10px 10px;' href='https://searchbusdev.000webhostapp.com/veiculo/" + veiculo.get().getId() + "'>Saiba Mais</a>" +
                    "</div>" +
                "</div>" +
            "</div>";

            MimeMessageHelper helper = new MimeMessageHelper(mail);
            
            helper.setTo(Email.getEmail());
            helper.setSubject("SearchBus - " + veiculo.get().getNome_comercial() +" | "+ veiculo.get().getMarca());
            helper.setText(html, true);
            mailSender.send(mail);

            response.put("status", "true");
            response.put("message", "Email foi enviado com sucesso !");

            return new ResponseEntity<> (response,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            
            response.put("status", "false");
            response.put("message", "Não foi possivel enviar email!");

            return new ResponseEntity<> (response,HttpStatus.OK);
        }
    }
}
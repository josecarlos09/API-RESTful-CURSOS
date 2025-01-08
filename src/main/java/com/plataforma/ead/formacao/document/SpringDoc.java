package com.plataforma.ead.formacao.document;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class SpringDoc {
    //http://localhost:8082/plataforma-ead-formacao/swagger-ui/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Plataforma de Educação Online, área da formação") // Titulo da API
                        .description("Microserviço: API RESTful para Plataforma EAD de Formação Acadêmica\n" +
                                "Esta aplicação é responsável pela gestão da formação acadêmica dos estudantes da instituição, oferecendo uma plataforma de Ensino a Distância (EAD) completa. A API RESTful permite o cadastro, acompanhamento e controle do progresso acadêmico dos alunos, incluindo a oferta de cursos, matrícula, avaliações e emissão de certificados. A plataforma visa proporcionar uma experiência de aprendizado flexível e acessível para todos os estudantes da instituição, utilizando as melhores práticas em desenvolvimento de microserviços.")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@plataforma-ead.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://plataforma-ead.com/api/licenca")));
    }
}
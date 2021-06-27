// TODO: eskport żądań z Postman

package edu.pja.sri.s23452.sri02;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Sri02Application {

    public static void main(String[] args) {

        SpringApplication.run(Sri02Application.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}


// TODO: 5. Wprowadź mechanizm HATEOAS dla zwracanych zasobów.
//         a. Dodaj link ‘self’ wskazujący jak ponowić żądanie, w wyniku które otrzymano
//          dany zasób
//         b. W przypadku zasobów posiadających powiązania, wskaż metodę
//          pozwalającą pobrać powiązane zasoby

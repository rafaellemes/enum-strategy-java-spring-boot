package br.com.lemes.enumstrategy.controller;

import br.com.lemes.enumstrategy.usecase.foo.FooUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EnumStrategyController {


    private final FooUseCase useCase;

    @GetMapping("/enum-strategy/foo/{id}")
    @ResponseBody
    public Map<String, String> run(@PathVariable("id") String id){
        return Map.of("impl", useCase.call(id));
    }


}

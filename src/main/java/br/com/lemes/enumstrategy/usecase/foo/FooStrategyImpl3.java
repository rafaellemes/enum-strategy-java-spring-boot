package br.com.lemes.enumstrategy.usecase.foo;

import org.springframework.stereotype.Component;

@Component
class FooStrategyImpl3 implements FooStrategy{

     @Override
     public String execute() {
         return "Foo Strategy Impl 3";
     }
 }

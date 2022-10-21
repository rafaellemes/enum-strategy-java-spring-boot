package br.com.lemes.enumstrategy.usecase.foo;

import org.springframework.stereotype.Component;
@Component
final class FooStrategyImpl1 implements FooStrategy{

     @Override
     public String execute() {
         return "Foo Strategy Impl 1";
     }
 }

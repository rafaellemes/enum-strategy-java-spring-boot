package br.com.lemes.enumstrategy.usecase.foo;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
class FooUseCaseImpl implements FooUseCase {

    private final ApplicationContext context;


    @Override
    public String call(String id){

        FooEnumStrategy fooEnumStrategy = FooEnumStrategy
                .findById(id)
                .orElseThrow(IllegalArgumentException::new); //Could be a Business Exception

        FooStrategy impl = fooEnumStrategy.getImpl(context);

        return impl.execute();
    }



    private enum FooEnumStrategy{

        IMPL1("Foo1", FooStrategyImpl1.class),
        IMPL2("Foo2", FooStrategyImpl2.class),
        IMPL3("Foo3", FooStrategyImpl3.class);

        FooEnumStrategy(String id, Class<? extends FooStrategy> impl) {
            this.id = id;
            this.impl = impl;
        }

        final String id;
        final Class<? extends FooStrategy> impl;

        public static Optional<FooEnumStrategy> findById(String id){

            //Ponto Importante: ao Decidir não usar a Ordenação natural
            //Corre o risco do Desenvolvedor erroneamente utilizar Identificadores iguais
            //para estratégias diferente, ocorrendo erro 'Silencioso' em tempo de Execução pegando
            // a Estratégia Errada
            //Uma Maneira de Tentar Diminuir as chances de erro é lançar uma exceção em tempo de execução
            //caso haja mais que um id
            //(Obs: Pode haver Estratégias iguais para Id diferentes e estar correto,
            // assim como poderia ser um erro de implementação


            Supplier<Stream<FooEnumStrategy>> fooEnumStrategyStream
                    = () -> Stream.of(values())
                    .filter(strategy -> strategy.id.equals(id));


            if(fooEnumStrategyStream.get().count() > 1){
                FooEnumStrategy fooEnumStrategy = fooEnumStrategyStream
                        .get()
                        .findFirst()
                        .orElseThrow();
                throw new RuntimeException (
                        String.format("Same id For 2 different Strategy %s ", fooEnumStrategy.id));
            }


            return fooEnumStrategyStream.get().findFirst();
        }

        public FooStrategy getImpl(ApplicationContext context) {

            return context.getBean(impl);
        }
    }

}

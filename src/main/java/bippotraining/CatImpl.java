package bippotraining;

import org.springframework.stereotype.Component;

@Component
public class CatImpl implements Cat {
    @Override
    public String say() {
        return "Cat say meow..";
    }
}

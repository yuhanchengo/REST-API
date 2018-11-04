package rest.repository;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class IdGenerator {
	
	private AtomicInteger nextId = new AtomicInteger(1);
	
	public int getNextId() {
		return nextId.getAndIncrement();
	}
	
	
}

package enteties;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class CompletedFormRepository {
    private final ConcurrentMap<Long, CompletedForm> repository = new ConcurrentHashMap<Long, CompletedForm>();
    private static AtomicLong counter = new AtomicLong();

    public void saveCompletedForm(CompletedForm completedForm){
        Long id = counter.incrementAndGet();
        completedForm.setId(id);
        repository.put(id, completedForm);
    }
    public Collection<CompletedForm> getList(){ // Возвращает список наших форм
        return repository.values();
    }

    public CompletedForm getById(Long id){
        return repository.get(id);
    }
}

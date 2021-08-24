package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class PostService {
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    // Не очень удобно, что id завязан и на отображение состояния нового объекта, и на сохранение объекта в мапу,
    // из-за этого приходится выстраивать вот такую логику.
    // Наверное, было бы вернее использовать enum для перечислений действий над объектом, тогда бы не пришлось писать
    // такую логику, а расширять программу было бы удобнее.

    public synchronized Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(repository.getNewId());
            return repository.save(post);
        }
        if (repository.isContainsKey(post.getId())) {
            return repository.change(post);
        }
        throw new NotFoundException("Post not found");
    }


    public void removeById(long id) {
        repository.removeById(id);
    }
}


package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class PostRepository {
    private Map<Long, Post> map = new ConcurrentHashMap<>();
    private AtomicLong id = new AtomicLong();

    public List<Post> all() {
        List<Post> list = new ArrayList<>();
        for (Post post : map.values()) {
            list.add(post);
        }
        return list;
    }

    public Optional<Post> getById(long id) {
        Optional<Post> post = Optional.of(map.get(id));
        return post;
    }

    public Post save(Post post) {
        map.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        map.remove(id);
    }

    public Post change(Post post) {
        map.get(post.getId()).setContent(post.getContent());
        return map.get(post.getId());
    }

    public boolean isContainsKey(Long id) {
        if (map.containsKey(id)) {
            return true;
        } else return false;
    }

    public Long getNewId() {
        return id.incrementAndGet();
    }

}

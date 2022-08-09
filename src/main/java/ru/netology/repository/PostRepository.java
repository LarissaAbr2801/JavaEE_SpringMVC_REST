package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

// Stub
@Repository
public class PostRepository {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger postsCounter = new AtomicInteger(0);

    public List<Post> all() {
        return posts.values()
                .stream()
                .filter(x -> !x.isRemoved())
                .toList();
    }

    public Optional<Post> getById(long id) {
        return posts.values().stream()
                .filter(x -> x.getId() == id)
                .filter(x -> !x.isRemoved())
                .findAny();
    }

    public Post save(Post post) {
        if (post.getId() != 0) {

            var postInList = getById(post.getId());

            if (postInList.isPresent() && !postInList.get().isRemoved()) {
                posts.put(postInList.get().getId(), post);
            } else {
                throw new NotFoundException("Невозможно сохранить пост!");
            }
        } else {
            post.setId(postsCounter.addAndGet(1));
            posts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        if (!posts.values().removeIf(post -> post.getId() == id)) {
            throw new NotFoundException();
        }
        Optional<Post> post = getById(id);
        post.ifPresent(value -> value.setRemoved(true));
    }
}

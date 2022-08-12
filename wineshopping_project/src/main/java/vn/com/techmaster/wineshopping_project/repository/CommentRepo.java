package vn.com.techmaster.wineshopping_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.techmaster.wineshopping_project.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, String> {
}

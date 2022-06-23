package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    public List<Comment> findByTrack(Track track);

}

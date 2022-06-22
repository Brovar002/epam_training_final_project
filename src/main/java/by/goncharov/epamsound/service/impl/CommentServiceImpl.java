package by.goncharov.epamsound.service.impl;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;
import by.goncharov.epamsound.dao.CommentRepository;
import by.goncharov.epamsound.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public void save(final Comment comment) {
        commentRepository.save(comment);
    }
    @Override
    public List<Comment> findCommentsByTrack(final Track track) {
        return commentRepository.findByTrack(track);
    }
}

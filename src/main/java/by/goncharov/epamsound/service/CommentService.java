package by.goncharov.epamsound.service;

import by.goncharov.epamsound.beans.Comment;
import by.goncharov.epamsound.beans.Track;

import java.util.List;

public interface CommentService {
    void save(Comment comment);
    List<Comment> findCommentsByTrack(Track track);
}

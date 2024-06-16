package com.sparta.layered_unit_testing.domain.comment.entity;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.sparta.layered_unit_testing.domain.board.entity.Board;
import com.sparta.layered_unit_testing.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {
    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();

    @Test
    @DisplayName("Comment 테스트")
    void commentCreateTest() {
        Comment comment = fixtureMonkey.giveMeOne(Comment.class);
        User user = fixtureMonkey.giveMeOne(User.class);
        Board board = fixtureMonkey.giveMeOne(Board.class);

        assertThat(comment.getContents());
        assertThat(board.getId());
        assertThat(user.getId());
    }

    @Test
    @DisplayName("CommentUpdate 테스트")
    void commentUpdateTest() {
        Comment comment = new Comment();
        Comment comment2 = fixtureMonkey.giveMeOne(Comment.class);

        comment.update(comment2.getContents());

        assertEquals(comment.getContents(), comment2.getContents());
    }
}
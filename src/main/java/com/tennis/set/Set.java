package com.tennis.set;

import com.tennis.common.ScoreHolder;
import com.tennis.common.TennisInformation;
import com.tennis.common.TennisSession;
import com.tennis.enums.SetStatus;
import com.tennis.listener.TennisListener;
import com.tennis.player.Player;

import java.util.Objects;

/**
 * Represents current set.
 */
public class Set extends ScoreHolder {

    private TennisListener listener;

    private Player winner ;

    public Set(TennisSession session, TennisListener listener) {

        this.initSetInformation(session);
        this.listener = listener;
    }


    @Override
    protected void updateScore(String id, Integer score) {
        scores.computeIfPresent(id, (k, v) -> {
            v.setScore(score);
            listener.displayScore(v);
            return v;
        });

    }

    @Override
    public int getScore(String id) {
        SetInformation setInformation = getTennisInformation(id);
        return setInformation.getScore();
    }

    @Override
    public void setWinner(Player player) {
        this.winner = player ;
        this.listener.endSet(winner);
    }


    /**
     * Get current status.
     *
     * @param id
     * @return
     */
    public SetStatus getCurrentStatus(String id) {
        SetInformation setInformation = getTennisInformation(id);
        return setInformation.getSetStatus();
    }

    /**
     * Set new status.
     *
     * @param id
     * @param status
     */
    public void setNewStatus(String id, SetStatus status) {
        scores.computeIfPresent(id, (k, v) -> {
            SetInformation g = SetInformation.class.cast(v);
            g.setSetStatus(status);
            return g;
        });
    }

    private void initSetInformation(TennisSession tennisSession) {

        tennisSession.getPlayers().entrySet().forEach(k -> {
            SetInformation setInformation = new SetInformation();
            setInformation.setSetStatus(SetStatus.NORMAL);
            setInformation.setScore(0);
            setInformation.setIdPlayer(k.getKey());
            setInformation.setName(k.getValue().getName());
            super.scores.put(k.getKey(), setInformation);
        });

    }

    private SetInformation getTennisInformation(String id) {
        TennisInformation tennisInformation = scores.getOrDefault(id, null);
        if (Objects.isNull(tennisInformation)) {
            throw new IllegalStateException(String.format("No player with id %s has been found", id));
        }
        return SetInformation.class.cast(tennisInformation);
    }
}

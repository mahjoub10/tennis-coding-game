package com.tennis.set;

import com.tennis.common.TennisInformation;
import com.tennis.enums.SetStatus;

/**
 * Holds current set information.
 */
public class SetInformation extends TennisInformation {

    private SetStatus setStatus ;

    public SetStatus getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(SetStatus setStatus) {
        this.setStatus = setStatus;
    }
}

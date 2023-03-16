package com.midlaj.resort.dao;

import com.midlaj.resort.entity.Resort;

public interface ResortDAO {

    Resort saveResort(Resort resort);

    Resort findResortById(Long id);

    public void changeEnabledStatus(Long id, Boolean enabledStatus);
}

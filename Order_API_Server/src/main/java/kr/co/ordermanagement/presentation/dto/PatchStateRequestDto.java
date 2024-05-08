package kr.co.ordermanagement.presentation.dto;

import kr.co.ordermanagement.domain.order.State;

public class PatchStateRequestDto {
    private State state;

    public PatchStateRequestDto() {
    }

    public PatchStateRequestDto(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}

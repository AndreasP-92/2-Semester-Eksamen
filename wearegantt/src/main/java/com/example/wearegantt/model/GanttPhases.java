package com.example.wearegantt.model;

public class GanttPhases {
    private int ganttPhase_id;
    private String ganttPhase_name;

    public GanttPhases(int ganttPhase_id, String ganttPhase_name) {
        this.ganttPhase_id = ganttPhase_id;
        this.ganttPhase_name = ganttPhase_name;
    }

    public int getGanttPhase_id() {
        return ganttPhase_id;
    }

    public void setGanttPhase_id(int ganttPhase_id) {
        this.ganttPhase_id = ganttPhase_id;
    }

    public String getGanttPhase_name() {
        return ganttPhase_name;
    }

    public void setGanttPhase_name(String ganttPhase_name) {
        this.ganttPhase_name = ganttPhase_name;
    }

    @Override
    public String toString() {
        return "GanttPhases{" +
                "ganttPhase_id=" + ganttPhase_id +
                ", ganttPhase_name='" + ganttPhase_name + '\'' +
                '}';
    }
}

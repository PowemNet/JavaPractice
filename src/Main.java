import com.powem.inv.algos.MissionPlanner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // TEST
        List<MissionPlanner.Resource> resources = Arrays.asList(
            new MissionPlanner.Resource("Astronaut", 5),
            new MissionPlanner.Resource("Equipment", 10)
        );

        List<MissionPlanner.Task> mission1Tasks = Arrays.asList(
            new MissionPlanner.Task("Mission1", "Task1", 0, 5, Map.of("Astronaut", 2, "Equipment", 3)),
            new MissionPlanner.Task("Mission1", "Task2", 5, 10, Map.of("Astronaut", 2, "Equipment", 2))
        );
        List<MissionPlanner.Task> mission2Tasks = Arrays.asList(
            new MissionPlanner.Task("Mission2", "Task1", 0, 4, Map.of("Astronaut", 3, "Equipment", 4)),
            new MissionPlanner.Task("Mission2", "Task2", 4, 8, Map.of("Astronaut", 3, "Equipment", 3))
        );

        List<MissionPlanner.Mission> missions = Arrays.asList(
            new MissionPlanner.Mission("Mission1", mission1Tasks),
            new MissionPlanner.Mission("Mission2", mission2Tasks)
        );

        MissionPlanner planner = new MissionPlanner(missions, resources);
        List<MissionPlanner.ScheduledTask> schedule = planner.generateSchedule();
        assert schedule.size() == 4;
        // TEST_END

        //TEST
        boolean noOverlap = true;
        for (int i = 0; i < schedule.size(); i++) {
            for (int j = i + 1; j < schedule.size(); j++) {
                MissionPlanner.ScheduledTask task1 = schedule.get(i);
                MissionPlanner.ScheduledTask task2 = schedule.get(j);
                if (task1.endTime > task2.startTime && task2.endTime > task1.startTime) {
                    for (String resource : task1.allocatedResources.keySet()) {
                        if (task1.allocatedResources.get(resource) + task2.allocatedResources.getOrDefault(resource, 0) > resources.stream()
                            .filter(r -> r.name.equals(resource)).findFirst().get().quantity) {
                            noOverlap = false;
                            break;
                        }
                    }
                }
            }
        }

        assert noOverlap;
        // TEST_END

        //TEST
        boolean validAllocations = true;
        for (MissionPlanner.ScheduledTask task : schedule) {
            for (String resource : task.allocatedResources.keySet()) {
                if (task.allocatedResources.get(resource) > resources.stream().filter(r -> r.name.equals(resource)).findFirst()
                    .get().quantity) {
                    validAllocations = false;
                    break;
                }
            }
        }

        assert validAllocations;
        // TEST_END

      //TEST
      try{
        new MissionPlanner.Resource("", 5);
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END


      //TEST
      try{
        new MissionPlanner.Resource("test", -1);
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

      //TEST
      try{
        new MissionPlanner.Resource(null, -1);
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

      //TEST
      try{
        new MissionPlanner.Task("", "Task1", 0, 5, Map.of("Astronaut", 2, "Equipment", 3));
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

      //TEST
      try{
        new MissionPlanner.Task("mission", null , 0, 5, Map.of("Astronaut", 2, "Equipment", 3));
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

      //TEST
      try{
        new MissionPlanner.Task("mission", null , -1, 5, Map.of("Astronaut", 2, "Equipment", 3));
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

      //TEST
      try{
        new MissionPlanner.Task("mission", "task1" , 0, -5, Map.of("Astronaut", 2, "Equipment", 3));
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

      //TEST
      try{
        new MissionPlanner.Task("mission", "task1" , 0, 5, null);
        assert false;
      } catch (IllegalArgumentException e) {
        assert true;
      }
      // TEST_END

    }
}
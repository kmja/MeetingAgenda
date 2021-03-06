package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class AgendaModel extends Observable implements Serializable {

	List<Day> days = new ArrayList<Day>();
	List<Activity> parkedActivities = new ArrayList<Activity>();

    int currentday = 0;
    int selectedact = 0;
    int selectedparked = 0;


	/**
	 * adds create and add a new day to model with starting time (hours and minutes)
	 */
	public Day addDay(int startHour, int startMin) {
        Day d = new Day(startHour, startMin);
        days.add(d);
        return d;
    }

	/**
	 * add an activity to model
	 */
	public void addActivity(Activity act, Day day, int position) {
		day.addActivity(act, position);
		setChanged();
		notifyObservers("ActivityAddedToDay");
	}
	
	/**
	 * add an activity to parked activities
	 */
	public void addParkedActivity(Activity act) {
		parkedActivities.add(act);
		setChanged();
		notifyObservers("ActivityParked");
	}
	
	/**
	 * remove an activity on provided position from parked activites 
	 */
	public Activity removeParkedActivity(int position) {
		Activity act =  parkedActivities.remove(position);
		setChanged();
		notifyObservers("ParkedActivityRemoved");
		return act;
	}

    public List<Activity> getParkedActivities(){
        return this.parkedActivities;
    }

	/**
	 * moves activity between the days, or day and parked activities.
	 * to park activity you need to set the newday to null
	 * to move a parked activity to let's say first day you set oldday to null
	 * and newday to first day instance
	 */
	public void moveActivityOld(Day oldday, int oldposition, Day newday, int newposition) {
		if(oldday != null && oldday == newday) {
			oldday.moveActivity(oldposition,newposition);
		} else if(oldday == null && newday != null) {
			Activity act = removeParkedActivity(oldposition);
			newday.addActivity(act,newposition);
		} else if(oldday != null && newday == null){
			Activity act = oldday.removeActivity(oldposition);
			addParkedActivity(act);
		} else if(oldday != null && newday != null) {
			Activity activity = oldday.removeActivity(oldposition);
			newday.addActivity(activity,newposition);
		}
		setChanged();
		notifyObservers();
	};

	
	/**
	 * you can use this method to create some test data and test your implementation
	 */
	public AgendaModel getModelWithExampleData() {
		AgendaModel model = new AgendaModel();

		Day d = model.addDay(8,0);
		model.addActivity(new Activity("Introduction","Intro to the meeting",10,1),d,0);
		model.addActivity(new Activity("Idea 1","Presenting idea 1",30,1),d,1);
		model.addActivity(new Activity("Working in groups","Working on business model for idea 1",35,2),d,2);
		model.addActivity(new Activity("Idea 1 discussion","Discussing the results of idea 1",15,3),d,3);
		model.addActivity(new Activity("Coffee break","Time for some coffee",20,4),d,4);

        Day day = model.addDay(7,45);
        model.addActivity(new Activity("Basketball","Play basketball",20,4),day,1);
        model.addActivity(new Activity("Paintball","Play paintball",20,4),day,2);

        model.addParkedActivity(new Activity("Business Time", "It's Time for Business",25,2));
        model.addParkedActivity(new Activity("Relax, Relax", "Take It Eeeeeeeaaaassyyyyyy...",45,4));
        model.addParkedActivity(new Activity("Roundtable", "Ready, Steady... TALK!",60,3));

        return model;
	}

    public List<Day> getDays(){

        return this.days;
    }


    public int getCurrentDay(){
        return currentday;
    }


    public void setCurrentDay(int i){
        this.currentday = i;
    }

    public int getSelectedActivity(){
        return selectedact;
    }
    public void setSelectedact (int i){
        this.selectedact = i;
    }

    public void removeActivity(int currentday, int selectedactivity){
        List<Activity> activities = this.getDays().get(currentday).getActivities();
        activities.remove(selectedactivity);
    }

    public int getSelectedParked() {
        return selectedparked;
    }
    public void setSelectedParked(int position){
        selectedparked = position;
    }

}
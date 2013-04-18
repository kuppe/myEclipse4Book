package com.example.e4.rcp.todo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

public class Todo {
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public static final String SUMMARY = "summary";
	private String summary;
	public static final String DESCRIPTION = "description";
	private String description;
	public static final String DONE = "done";
	private boolean done;
	public static final String DUEDATE = "dueDate";
	private Date dueDate;
	public static final String ID = "id";
	private long id;

	// Initializes with defaults
	public Todo() {
		this.summary = "";
		this.description = "";
		this.dueDate = new Date();
	}

	private Todo(Todo todo) {
		this(todo.id, todo.summary, todo.description, todo.done, todo.dueDate);
	}

	public Todo(long id, String summary, String description, boolean done,
			Date dueDate) {
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.done = done;
		this.dueDate = dueDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		propertyChangeSupport.firePropertyChange(SUMMARY, this.summary,
				this.summary = summary);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		propertyChangeSupport.firePropertyChange(DESCRIPTION, this.description,
				this.description = description);
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		propertyChangeSupport.firePropertyChange(DONE, this.done,
				this.done = done);
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		propertyChangeSupport.firePropertyChange(DUEDATE, this.dueDate,
				this.dueDate = dueDate);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		propertyChangeSupport.firePropertyChange(ID, this.id, this.id = id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Todo [summary=" + summary + ", description=" + description
				+ ", done=" + done + ", dueDate=" + dueDate + ", id=" + id
				+ "]";
	}

	public Todo copy() {
		return new Todo(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}

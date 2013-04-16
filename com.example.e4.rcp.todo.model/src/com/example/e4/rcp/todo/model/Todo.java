package com.example.e4.rcp.todo.model;

import java.util.Date;

public class Todo {
	private String summary;
	private String description;
	private boolean done;
	private Date dueDate;
	private long id;

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
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
}

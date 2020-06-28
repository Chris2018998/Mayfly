/*
 * Copyright (C) Chris Liao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.mayfly.test.interception.object;

/**
 * A bean class for IOC test.
 *
 * @author Chris Liao
 */
public class Child implements Young {

  /**
   * name
   */
  private String name;

  /**
   * age
   */
  private int age;

  /**
   * Return man age
   */
  public int getAge() {
    return age;
  }

  /**
   * set Age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * return name
   */
  public String getName() {
    return name;
  }

  /**
   * Set name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Print hello
   */
  public void sayHello(String target){
    System.out.println("Hello,"+ target);
  }
}
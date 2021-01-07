package com.arlenchen.pojo.vo;

/**
 * @author arlenchen
 */
public class OrderStatusCountsVO {
 private  Integer waitPayCounts;
 private  Integer waitDeliverCounts;
 private  Integer waitReceiveCounts;
 private  Integer waitCommentCounts;

 public Integer getWaitPayCounts() {
  return waitPayCounts;
 }

 public void setWaitPayCounts(Integer waitPayCounts) {
  this.waitPayCounts = waitPayCounts;
 }

 public Integer getWaitDeliverCounts() {
  return waitDeliverCounts;
 }

 public void setWaitDeliverCounts(Integer waitDeliverCounts) {
  this.waitDeliverCounts = waitDeliverCounts;
 }

 public Integer getWaitReceiveCounts() {
  return waitReceiveCounts;
 }

 public void setWaitReceiveCounts(Integer waitReceiveCounts) {
  this.waitReceiveCounts = waitReceiveCounts;
 }

 public Integer getWaitCommentCounts() {
  return waitCommentCounts;
 }

 public void setWaitCommentCounts(Integer waitCommentCounts) {
  this.waitCommentCounts = waitCommentCounts;
 }

 @Override
 public String toString() {
  return "OrderStatusCountVO{" +
          "waitPayCounts=" + waitPayCounts +
          ", waitDeliverCounts=" + waitDeliverCounts +
          ", waitReceiveCounts=" + waitReceiveCounts +
          ", waitCommentCounts=" + waitCommentCounts +
          '}';
 }
}

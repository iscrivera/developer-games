package com.redhat.challenge.discount.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Objects;

@RegisterForReflection
public class DiscountCode {

   private String name;
   private Integer amount;
   private String enterprise;
   private DiscountCodeType type;
   private Integer used;
   private Long expire;
   private Integer globalUsed;

   public DiscountCode() {
   }

   public DiscountCode(String name, Integer amount, String enterprise, DiscountCodeType type, Integer used, Long expire, Integer globalUsed) {
      this.name = name;
      this.amount = amount;
      this.enterprise = enterprise;
      this.type = type;
      this.used = used;
      this.expire = expire;
      this.globalUsed = globalUsed;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getAmount() {
      return amount;
   }

   public void setAmount(Integer amount) {
      this.amount = amount;
   }

   public String getEnterprise() {
      return enterprise;
   }

   public void setEnterprise(String enterprise) {
      this.enterprise = enterprise;
   }

   public DiscountCodeType getType() {
      return type;
   }

   public void setType(DiscountCodeType type) {
      this.type = type;
   }

   public Integer getUsed() {
      return used;
   }

   public void setUsed(Integer used) {
      this.used = used;
   }

   public Integer getGlobalUsed() {
      return globalUsed;
   }

   public void setGlobalUsed(Integer globalUsed) {
      this.globalUsed = globalUsed;
   }

   public Long getExpire() {
      return expire;
   }

   public void setExpire(Long expire) {
      this.expire = expire;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      DiscountCode that = (DiscountCode) o;
      return Objects.equals(name, that.name) && Objects.equals(amount, that.amount) && Objects
            .equals(enterprise, that.enterprise) && type == that.type && Objects.equals(used, that.used) && Objects.equals(expire, that.expire);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name, amount, enterprise, type, used);
   }

   @Override
   public String toString() {
      return "DiscountCode{" + "name='" + name + '\'' + ", amount=" + amount + ", enterprise='" + enterprise + '\''
            + ", type=" + type + ", used=" + used + ", expire=" + expire + '}';
   }
}

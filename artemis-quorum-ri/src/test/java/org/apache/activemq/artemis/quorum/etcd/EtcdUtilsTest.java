/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.quorum.etcd;

import org.junit.Assert;
import org.junit.Test;

public class EtcdUtilsTest {

   @Test
   public void assertNotInterruptedExceptionWithNullShouldPass() throws InterruptedException {
      EtcdUtils.assertNoNestedInterruptedException(null);
      Assert.assertEquals("No error should be thrown", true, true);
   }

   @Test
   public void assertNotInterruptedExceptionWithNPEShouldPass() throws InterruptedException {
      EtcdUtils.assertNoNestedInterruptedException(new NullPointerException());
      Assert.assertEquals("No error should be thrown", true, true);
   }

   @Test(expected = InterruptedException.class)
   public void assertNotInterruptedExceptionWithInterruptedShouldThrowInterrupted() throws InterruptedException {
      EtcdUtils.assertNoNestedInterruptedException(new InterruptedException("DUMMY FOR TESTING PURPOSE"));
   }

   @Test(expected = InterruptedException.class)
   public void assertNotInterruptedExceptionWithWrappedInterruptedShouldThrowInterrupted() throws InterruptedException {
      EtcdUtils.assertNoNestedInterruptedException(
         new RuntimeException(
            new InterruptedException("DUMMY FOR TESTING PURPOSE")));
   }

   @Test
   public void assertNotInterruptedExceptionWithCycleAndNoInterruptedShoudPass() throws InterruptedException {
      final TestException a = new TestException("DUMMY FOR TESTING PURPOSE 1");
      final TestException b = new TestException("DUMMY FOR TESTING PURPOSE 2");
      a.setCause(b);
      b.setCause(a);
      EtcdUtils.assertNoNestedInterruptedException(
         new RuntimeException("DUMMY FOR TESTING PURPOSE", a));
      Assert.assertEquals("Nested exception is supported", true, true);
   }

   private static class TestException extends Throwable {
      private Throwable cause;

      public TestException(String message) {
         super(message);
      }
      @Override
      public Throwable getCause() {
         return cause;
      }
      public void setCause(Throwable cause) {
         this.cause = cause;
      }
   }
}

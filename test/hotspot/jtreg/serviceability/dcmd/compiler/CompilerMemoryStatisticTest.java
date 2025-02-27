/*
 * Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023, Red Hat, Inc. and/or its affiliates.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import jdk.test.lib.dcmd.PidJcmdExecutor;
import jdk.test.lib.process.OutputAnalyzer;

import java.util.Iterator;

/*
 * @test CompilerMemoryStatisticTest
 * @summary Test Compiler.memory
 * @requires vm.compiler1.enabled
 * @requires vm.compiler2.enabled
 *
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:CompileCommand=memstat,*.* CompilerMemoryStatisticTest
 */

/*
 * @test CompilerMemoryStatisticTest
 * @summary Test Compiler.memory
 * @requires vm.compiler1.enabled
 * @requires vm.compiler2.enabled
 *
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run main/othervm -XX:CompileCommand=memstat,*.*,collect CompilerMemoryStatisticTest
 */

public class CompilerMemoryStatisticTest {

    public static void main(String args[]) throws Exception {
        PidJcmdExecutor executor = new PidJcmdExecutor();
        OutputAnalyzer out = executor.execute("Compiler.memory");
        out.shouldHaveExitValue(0);

        // Looks like this:
        // total     NA        RA        result  #nodes  time    type  #rc thread              method
        // 211488    66440     77624     ok      13      0.057   c2    2   0x00007fb49428db70  compiler/print/CompileCommandPrintMemStat$TestMain::method1(()V)
        out.shouldMatch("total.*method");
        out.shouldMatch("\\d+ +\\d+ +\\d+ +\\S+ +\\d+.*java.*\\(.*\\)");
    }
}

"use client";

import { RequireAuth } from "@/components/providers/require-auth";
import { AppShell } from "@/components/logout/app-shell";
import { RepoDashboard } from "@/components/dashboard/repo-dasboard";

export default function DashboardPage() {
  return (
    <RequireAuth>
      <AppShell hideHeader>
        <RepoDashboard/>
      </AppShell>
    </RequireAuth>
  );
}
    
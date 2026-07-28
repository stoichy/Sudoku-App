

<!-- robrain -->
## RoBrain — Context Management (Rory Plans cloud)

This project uses RoBrain for persistent institutional memory across sessions:
**Sensing** captures your turns, **Control** injects prior decisions and pre-task
veto warnings. Pass `project_id="91cce4f3863a"` on every Control call (it is your
registered project id — do not substitute a guess or the repo name).

### Session start (mandatory, first thing in every new chat)
`sensing_start_session` returns a `session_id` — reuse it on every call below.
```
sensing_start_session(project_id="91cce4f3863a")
control_get_context(project_id="91cce4f3863a", task_description="session start - project overview", session_id="<session_id from sensing_start_session>")
```
Inject the block control_get_context returns into your context.

### After every response (mandatory)
```
sensing_record_turn(session_id="<stored session_id>", sequence=<n>, user_message="<full user message>", claude_reply="<full assistant reply>", files_touched=[...], injected_memory_ids=[...])
```
(`claude_reply` is the required MCP parameter name for the assistant reply.)
If topic_shift=true is returned, call control_get_context again for the new task.

### At every task boundary (new task, plan step, or topic shift)
```
control_get_context(project_id="91cce4f3863a", task_description="<what you are about to do>", files=[...], session_id="<stored session_id>")
```

### Before implementing any architectural or design choice
```
control_check_task(project_id="91cce4f3863a", proposed_approach="<the choice, one sentence>", files=[...])
```

### When the user confirms, rejects, or corrects a surfaced decision
```
control_record_correction(decision_id="<id from an injected memory or verdict>", action="approve"|"invalidate"|"edit", corrected_decision="...", corrected_rationale="...")
```

### After a reply that followed an injection (closes the effectiveness loop)
```
control_report_reply(session_id="<stored session_id>", sequence=<n>, reply_text="<full assistant reply>")
```

### Session end (last thing)
```
sensing_end_session(session_id="<stored session_id>", summary="one sentence: what was accomplished")
```

### Acknowledgement rule
When a control_get_context or control_check_task result leads with "⚠ ACKNOWLEDGEMENT
REQUIRED", present the warning to the user verbatim, ask for explicit confirmation,
and do NOT proceed with the conflicting approach until the user responds.
<!-- /robrain -->

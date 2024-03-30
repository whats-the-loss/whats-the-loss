## Deployment

The most up-to-date version of the api can be found at [GitHub Pages](https://whats-the-loss.github.io/whats-the-loss/).

## Topology

In the following the relation between the basic entities of wtl is shown:

- `project` (contains multiple experiments)
    - experiment path (each experiment can have a parent folder path within a project)
        - `experiment` (contains multiple groups/runs)
            - `group` (arbitrary nested tree of groups, a run can also have no parent group)
                - `run` (contains logged values)
                    - logged values within a run
                        - each logged value: key, optional type, ~~optional tags~~

### Project

A user can work on and thus be a member of multiple projects.
Each project contains multiple experiments.
Experiments are stored in folder within the project. Thereby an experiment does not need to have a parent folder, it can
also be stored in the 'root' folder.

### Experiment

Each experiment contains multiple runs which can be grouped by `groups`.

### Group

Runs can be clustered in groups. This allows to create a hierarchical structure and enables to aggregate values over
multiple runs within a group (e.g. average value for each timestamp). Groups can also be nested.

### Run

A run has a configuration (list of hyperparameters as a json object) and allows to log multiple values/log-entries over
time and steps.
A log-entry contains multiple values and is assassinated with a timestamp and optional step.

Each value is identified by a key (which is a flatted json path).
A logged value can be a scalar, vector, media object (image, video, ...) or dataframe containing multiple columns (which
can be displayed as a chart for a specific step).

Example for a log-entry:

```json
{
  "step": 100,
  "timestamp": "2024-01-09T12:20:23.511Z",
  "values": {
    "training.loss.regulation1": 10,
    "training.loss": 22,
    "evaluation.max_joint_velocities": [
      1,
      2,
      3
    ]
  }
}
```

#### Types and tags for logged values

Each key for a logged value can have an optional associated type ~~and tags~~.
A type has a name and optional unit, 'optimal' field (is a higher or lower value better) and unit field.
An example for a type is:

```json
{
  "name": "loss",
  "optimal": "low"
}
```

Another example:

```json
{
  "name": "velocity",
  "unit": "m/s"
}
```

#### Metrics

Each logged value can be a metric that allows to reason about the optimality/score of a run.
This can be done by defining a list of metrics by referring to the corresponding value keys.
An example metric list for a run:

```json
[
  {
    "key": "training.loss",
    // when a value has an assisicated type with this field this value can also be inferred
    "optimal": "low"
  },
  {
    "key": "evaluation.reward",
    "optimal": "high"
  }
]
```
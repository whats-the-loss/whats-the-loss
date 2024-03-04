import { Component } from '@angular/core';
import {
  MatTree,
  MatTreeFlatDataSource,
  MatTreeFlattener,
  MatTreeNode, MatTreeNodeDef,
  MatTreeNodePadding,
  MatTreeNodeToggle
} from "@angular/material/tree";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {FlatTreeControl} from "@angular/cdk/tree";


interface FlatNode {
  expandable: boolean;
  name: string;
  level: number;
}


interface Group {
  name: string;
  children: (Group | Run) []
}

interface Run {
  id: string
  name: string;
}


@Component({
  selector: 'wtl-runs-sidepanel',
  standalone: true,
  imports: [
    MatTree,
    MatTreeNode,
    MatIcon,
    MatIconButton,
    MatTreeNodeToggle,
    MatTreeNodePadding,
    MatTreeNodeDef
  ],
  templateUrl: './runs-sidepanel.component.html',
  styleUrl: './runs-sidepanel.component.scss'
})
export class RunsSidepanelComponent {
  runs: (Group | Run)[] = [
    {id: "0", name: 'Run 0'},
    {id: "1", name: 'Run 1'},
    {id: "2", name: 'Run 2'},
    {id: "3", name: 'Run 3'},
  ];


  treeControl = new FlatTreeControl<FlatNode>(
    node => node.level,
    node => node.expandable,
  );

  private _transformer = (node: Group | Run, level: number) => {
    const hasChildren = "children" in node ? (node.children && node.children.length > 0) : false
    return {
      expandable: hasChildren,
      name: node.name,
      level: level,
    };
  };

  treeFlattener = new MatTreeFlattener(
    this._transformer,
    node => node.level,
    node => node.expandable,
    node => ('children' in node) ? node.children : [],
  );

  treeDataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor() {
    this.treeDataSource.data = this.runs;
  }

  hasChild = (_: number, node: FlatNode) => node.expandable;
}

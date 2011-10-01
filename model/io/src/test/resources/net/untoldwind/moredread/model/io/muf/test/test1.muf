<?xml version="1.0" encoding="UTF-8"?>

<scene>
  <name>Scene</name>
  <localTranslation x="0.0" y="0.0" z="0.0"/>
  <localScale x="1.0" y="1.0" z="1.0"/>
  <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
  <children size="14">
    <element class="net.untoldwind.moredread.model.scene.GeneratorNode">
      <name>Cube</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="0.0" y="0.0" z="0.0"/>
      <localScale x="2.0" y="2.0" z="2.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <meshGenerator class="net.untoldwind.moredread.model.generator.CubeMeshGenerator">
        <center x="0.0" y="0.0" z="0.0"/>
        <size>1.0</size>
      </meshGenerator>
      <children size="0"/>
    </element>
    <element class="net.untoldwind.moredread.model.scene.MeshNode">
      <name>Mesh Cube</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="3.5" y="0.0" z="0.0"/>
      <localScale x="1.0" y="1.0" z="1.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
        <numVertices>8</numVertices>
        <vertex x="-1.0" y="-1.0" z="-1.0"/>
        <vertex x="1.0" y="-1.0" z="-1.0"/>
        <vertex x="1.0" y="1.0" z="-1.0"/>
        <vertex x="-1.0" y="1.0" z="-1.0"/>
        <vertex x="-1.0" y="-1.0" z="1.0"/>
        <vertex x="1.0" y="-1.0" z="1.0"/>
        <vertex x="1.0" y="1.0" z="1.0"/>
        <vertex x="-1.0" y="1.0" z="1.0"/>
        <numFaces>6</numFaces>
        <index>3</index>
        <index>2</index>
        <index>1</index>
        <index>0</index>
        <index>4</index>
        <index>5</index>
        <index>6</index>
        <index>7</index>
        <index>4</index>
        <index>7</index>
        <index>3</index>
        <index>0</index>
        <index>1</index>
        <index>2</index>
        <index>6</index>
        <index>5</index>
        <index>0</index>
        <index>1</index>
        <index>5</index>
        <index>4</index>
        <index>2</index>
        <index>3</index>
        <index>7</index>
        <index>6</index>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.GeneratorNode">
      <name>Octahedron</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-3.5" y="0.0" z="0.0"/>
      <localScale x="1.0" y="1.0" z="1.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <meshGenerator class="net.untoldwind.moredread.model.generator.OctahedronMeshGenerator">
        <center x="0.0" y="0.0" z="0.0"/>
        <size>1.0</size>
      </meshGenerator>
      <children size="0"/>
    </element>
    <element class="net.untoldwind.moredread.model.scene.GeneratorNode">
      <name>Dodecahedron</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="10.0" y="0.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <meshGenerator class="net.untoldwind.moredread.model.generator.DodecahedronMeshGenerator">
        <center x="0.0" y="0.0" z="0.0"/>
        <size>1.0</size>
      </meshGenerator>
      <children size="0"/>
    </element>
    <element class="net.untoldwind.moredread.model.scene.MeshNode">
      <name>Mesh Dodecahedron</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-10.0" y="0.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.PolyMesh">
        <numVertices>20</numVertices>
        <vertex x="0.57735026" y="0.57735026" z="0.57735026"/>
        <vertex x="0.57735026" y="0.57735026" z="-0.57735026"/>
        <vertex x="0.57735026" y="-0.57735026" z="0.57735026"/>
        <vertex x="0.57735026" y="-0.57735026" z="-0.57735026"/>
        <vertex x="-0.57735026" y="0.57735026" z="0.57735026"/>
        <vertex x="-0.57735026" y="0.57735026" z="-0.57735026"/>
        <vertex x="-0.57735026" y="-0.57735026" z="0.57735026"/>
        <vertex x="-0.57735026" y="-0.57735026" z="-0.57735026"/>
        <vertex x="0.3568221" y="0.93417233" z="0.0"/>
        <vertex x="-0.3568221" y="0.93417233" z="0.0"/>
        <vertex x="0.3568221" y="-0.93417233" z="0.0"/>
        <vertex x="-0.3568221" y="-0.93417233" z="0.0"/>
        <vertex x="0.93417233" y="0.0" z="0.3568221"/>
        <vertex x="0.93417233" y="0.0" z="-0.3568221"/>
        <vertex x="-0.93417233" y="0.0" z="0.3568221"/>
        <vertex x="-0.93417233" y="0.0" z="-0.3568221"/>
        <vertex x="0.0" y="0.3568221" z="0.93417233"/>
        <vertex x="0.0" y="-0.3568221" z="0.93417233"/>
        <vertex x="0.0" y="0.3568221" z="-0.93417233"/>
        <vertex x="0.0" y="-0.3568221" z="-0.93417233"/>
        <numFaces>12</numFaces>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>0</index>
        <index>8</index>
        <index>9</index>
        <index>4</index>
        <index>16</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>0</index>
        <index>12</index>
        <index>13</index>
        <index>1</index>
        <index>8</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>0</index>
        <index>16</index>
        <index>17</index>
        <index>2</index>
        <index>12</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>8</index>
        <index>1</index>
        <index>18</index>
        <index>5</index>
        <index>9</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>12</index>
        <index>2</index>
        <index>10</index>
        <index>3</index>
        <index>13</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>16</index>
        <index>4</index>
        <index>14</index>
        <index>6</index>
        <index>17</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>9</index>
        <index>5</index>
        <index>15</index>
        <index>14</index>
        <index>4</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>6</index>
        <index>11</index>
        <index>10</index>
        <index>2</index>
        <index>17</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>3</index>
        <index>19</index>
        <index>18</index>
        <index>1</index>
        <index>13</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>7</index>
        <index>15</index>
        <index>5</index>
        <index>18</index>
        <index>19</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>7</index>
        <index>11</index>
        <index>6</index>
        <index>14</index>
        <index>15</index>
        <numStips>1</numStips>
        <stripCount>5</stripCount>
        <index>7</index>
        <index>19</index>
        <index>3</index>
        <index>10</index>
        <index>11</index>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.MeshNode">
      <name>Mesh Octahedron</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="0.0" y="10.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.TriangleMesh">
        <numVertices>6</numVertices>
        <vertex x="1.0" y="0.0" z="0.0"/>
        <vertex x="-1.0" y="0.0" z="0.0"/>
        <vertex x="0.0" y="1.0" z="0.0"/>
        <vertex x="0.0" y="-1.0" z="0.0"/>
        <vertex x="0.0" y="0.0" z="1.0"/>
        <vertex x="0.0" y="0.0" z="-1.0"/>
        <numFaces>8</numFaces>
        <index>4</index>
        <index>0</index>
        <index>2</index>
        <index>4</index>
        <index>2</index>
        <index>1</index>
        <index>4</index>
        <index>1</index>
        <index>3</index>
        <index>4</index>
        <index>3</index>
        <index>0</index>
        <index>5</index>
        <index>2</index>
        <index>0</index>
        <index>5</index>
        <index>1</index>
        <index>2</index>
        <index>5</index>
        <index>3</index>
        <index>1</index>
        <index>5</index>
        <index>0</index>
        <index>3</index>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.MeshNode">
      <name>Cube With Hole</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="0.0" y="-10.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.PolyMesh">
        <numVertices>16</numVertices>
        <vertex x="-1.0" y="-1.0" z="-1.0"/>
        <vertex x="1.0" y="-1.0" z="-1.0"/>
        <vertex x="1.0" y="1.0" z="-1.0"/>
        <vertex x="-1.0" y="1.0" z="-1.0"/>
        <vertex x="-1.0" y="-1.0" z="1.0"/>
        <vertex x="1.0" y="-1.0" z="1.0"/>
        <vertex x="1.0" y="1.0" z="1.0"/>
        <vertex x="-1.0" y="1.0" z="1.0"/>
        <vertex x="-0.75" y="-0.75" z="-1.0"/>
        <vertex x="0.75" y="-0.75" z="-1.0"/>
        <vertex x="0.75" y="0.75" z="-1.0"/>
        <vertex x="-0.75" y="0.75" z="-1.0"/>
        <vertex x="-0.75" y="-0.75" z="1.0"/>
        <vertex x="0.75" y="-0.75" z="1.0"/>
        <vertex x="0.75" y="0.75" z="1.0"/>
        <vertex x="-0.75" y="0.75" z="1.0"/>
        <numFaces>10</numFaces>
        <numStips>2</numStips>
        <stripCount>4</stripCount>
        <index>3</index>
        <index>2</index>
        <index>1</index>
        <index>0</index>
        <stripCount>4</stripCount>
        <index>8</index>
        <index>9</index>
        <index>10</index>
        <index>11</index>
        <numStips>2</numStips>
        <stripCount>4</stripCount>
        <index>4</index>
        <index>5</index>
        <index>6</index>
        <index>7</index>
        <stripCount>4</stripCount>
        <index>15</index>
        <index>14</index>
        <index>13</index>
        <index>12</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>4</index>
        <index>7</index>
        <index>3</index>
        <index>0</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>1</index>
        <index>2</index>
        <index>6</index>
        <index>5</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>0</index>
        <index>1</index>
        <index>5</index>
        <index>4</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>2</index>
        <index>3</index>
        <index>7</index>
        <index>6</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>8</index>
        <index>12</index>
        <index>13</index>
        <index>9</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>10</index>
        <index>14</index>
        <index>15</index>
        <index>11</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>13</index>
        <index>14</index>
        <index>10</index>
        <index>9</index>
        <numStips>1</numStips>
        <stripCount>4</stripCount>
        <index>11</index>
        <index>15</index>
        <index>12</index>
        <index>8</index>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.GeneratorNode">
      <name>Boolean INTERSECTION</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-10.0" y="-10.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <meshGenerator class="net.untoldwind.moredread.model.generator.BooleanGenerator">
        <operation>INTERSECTION</operation>
      </meshGenerator>
      <children size="2">
        <element class="net.untoldwind.moredread.model.scene.MeshNode">
          <name>Cube1</name>
          <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
          <localTranslation x="0.0" y="0.0" z="0.0"/>
          <localScale x="1.0" y="1.0" z="1.0"/>
          <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
          <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
            <numVertices>8</numVertices>
            <vertex x="-1.0" y="-1.0" z="-1.0"/>
            <vertex x="1.0" y="-1.0" z="-1.0"/>
            <vertex x="1.0" y="1.0" z="-1.0"/>
            <vertex x="-1.0" y="1.0" z="-1.0"/>
            <vertex x="-1.0" y="-1.0" z="1.0"/>
            <vertex x="1.0" y="-1.0" z="1.0"/>
            <vertex x="1.0" y="1.0" z="1.0"/>
            <vertex x="-1.0" y="1.0" z="1.0"/>
            <numFaces>6</numFaces>
            <index>3</index>
            <index>2</index>
            <index>1</index>
            <index>0</index>
            <index>4</index>
            <index>5</index>
            <index>6</index>
            <index>7</index>
            <index>4</index>
            <index>7</index>
            <index>3</index>
            <index>0</index>
            <index>1</index>
            <index>2</index>
            <index>6</index>
            <index>5</index>
            <index>0</index>
            <index>1</index>
            <index>5</index>
            <index>4</index>
            <index>2</index>
            <index>3</index>
            <index>7</index>
            <index>6</index>
          </geometry>
        </element>
        <element class="net.untoldwind.moredread.model.scene.MeshNode">
          <name>Cube2</name>
          <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
          <localTranslation x="0.0" y="0.0" z="0.0"/>
          <localScale x="1.0" y="1.0" z="1.0"/>
          <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
          <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
            <numVertices>8</numVertices>
            <vertex x="-0.9" y="-0.8" z="-0.7"/>
            <vertex x="1.1" y="-0.8" z="-0.7"/>
            <vertex x="1.1" y="1.2" z="-0.7"/>
            <vertex x="-0.9" y="1.2" z="-0.7"/>
            <vertex x="-0.9" y="-0.8" z="1.3"/>
            <vertex x="1.1" y="-0.8" z="1.3"/>
            <vertex x="1.1" y="1.2" z="1.3"/>
            <vertex x="-0.9" y="1.2" z="1.3"/>
            <numFaces>6</numFaces>
            <index>3</index>
            <index>2</index>
            <index>1</index>
            <index>0</index>
            <index>4</index>
            <index>5</index>
            <index>6</index>
            <index>7</index>
            <index>4</index>
            <index>7</index>
            <index>3</index>
            <index>0</index>
            <index>1</index>
            <index>2</index>
            <index>6</index>
            <index>5</index>
            <index>0</index>
            <index>1</index>
            <index>5</index>
            <index>4</index>
            <index>2</index>
            <index>3</index>
            <index>7</index>
            <index>6</index>
          </geometry>
        </element>
      </children>
    </element>
    <element class="net.untoldwind.moredread.model.scene.GeneratorNode">
      <name>Boolean UNION</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-20.0" y="-10.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <meshGenerator class="net.untoldwind.moredread.model.generator.BooleanGenerator">
        <operation>UNION</operation>
      </meshGenerator>
      <children size="2">
        <element class="net.untoldwind.moredread.model.scene.MeshNode">
          <name>Cube1</name>
          <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
          <localTranslation x="0.0" y="0.0" z="0.0"/>
          <localScale x="1.0" y="1.0" z="1.0"/>
          <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
          <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
            <numVertices>8</numVertices>
            <vertex x="-1.0" y="-1.0" z="-1.0"/>
            <vertex x="1.0" y="-1.0" z="-1.0"/>
            <vertex x="1.0" y="1.0" z="-1.0"/>
            <vertex x="-1.0" y="1.0" z="-1.0"/>
            <vertex x="-1.0" y="-1.0" z="1.0"/>
            <vertex x="1.0" y="-1.0" z="1.0"/>
            <vertex x="1.0" y="1.0" z="1.0"/>
            <vertex x="-1.0" y="1.0" z="1.0"/>
            <numFaces>6</numFaces>
            <index>3</index>
            <index>2</index>
            <index>1</index>
            <index>0</index>
            <index>4</index>
            <index>5</index>
            <index>6</index>
            <index>7</index>
            <index>4</index>
            <index>7</index>
            <index>3</index>
            <index>0</index>
            <index>1</index>
            <index>2</index>
            <index>6</index>
            <index>5</index>
            <index>0</index>
            <index>1</index>
            <index>5</index>
            <index>4</index>
            <index>2</index>
            <index>3</index>
            <index>7</index>
            <index>6</index>
          </geometry>
        </element>
        <element class="net.untoldwind.moredread.model.scene.MeshNode">
          <name>Cube2</name>
          <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
          <localTranslation x="0.0" y="0.0" z="0.0"/>
          <localScale x="1.0" y="1.0" z="1.0"/>
          <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
          <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
            <numVertices>8</numVertices>
            <vertex x="-0.9" y="-0.8" z="-0.7"/>
            <vertex x="1.1" y="-0.8" z="-0.7"/>
            <vertex x="1.1" y="1.2" z="-0.7"/>
            <vertex x="-0.9" y="1.2" z="-0.7"/>
            <vertex x="-0.9" y="-0.8" z="1.3"/>
            <vertex x="1.1" y="-0.8" z="1.3"/>
            <vertex x="1.1" y="1.2" z="1.3"/>
            <vertex x="-0.9" y="1.2" z="1.3"/>
            <numFaces>6</numFaces>
            <index>3</index>
            <index>2</index>
            <index>1</index>
            <index>0</index>
            <index>4</index>
            <index>5</index>
            <index>6</index>
            <index>7</index>
            <index>4</index>
            <index>7</index>
            <index>3</index>
            <index>0</index>
            <index>1</index>
            <index>2</index>
            <index>6</index>
            <index>5</index>
            <index>0</index>
            <index>1</index>
            <index>5</index>
            <index>4</index>
            <index>2</index>
            <index>3</index>
            <index>7</index>
            <index>6</index>
          </geometry>
        </element>
      </children>
    </element>
    <element class="net.untoldwind.moredread.model.scene.GeneratorNode">
      <name>Boolean DIFFERENCE</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-30.0" y="-10.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <meshGenerator class="net.untoldwind.moredread.model.generator.BooleanGenerator">
        <operation>DIFFERENCE</operation>
      </meshGenerator>
      <children size="2">
        <element class="net.untoldwind.moredread.model.scene.MeshNode">
          <name>Cube1</name>
          <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
          <localTranslation x="0.0" y="0.0" z="0.0"/>
          <localScale x="1.0" y="1.0" z="1.0"/>
          <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
          <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
            <numVertices>8</numVertices>
            <vertex x="-1.0" y="-1.0" z="-1.0"/>
            <vertex x="1.0" y="-1.0" z="-1.0"/>
            <vertex x="1.0" y="1.0" z="-1.0"/>
            <vertex x="-1.0" y="1.0" z="-1.0"/>
            <vertex x="-1.0" y="-1.0" z="1.0"/>
            <vertex x="1.0" y="-1.0" z="1.0"/>
            <vertex x="1.0" y="1.0" z="1.0"/>
            <vertex x="-1.0" y="1.0" z="1.0"/>
            <numFaces>6</numFaces>
            <index>3</index>
            <index>2</index>
            <index>1</index>
            <index>0</index>
            <index>4</index>
            <index>5</index>
            <index>6</index>
            <index>7</index>
            <index>4</index>
            <index>7</index>
            <index>3</index>
            <index>0</index>
            <index>1</index>
            <index>2</index>
            <index>6</index>
            <index>5</index>
            <index>0</index>
            <index>1</index>
            <index>5</index>
            <index>4</index>
            <index>2</index>
            <index>3</index>
            <index>7</index>
            <index>6</index>
          </geometry>
        </element>
        <element class="net.untoldwind.moredread.model.scene.MeshNode">
          <name>Cube2</name>
          <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
          <localTranslation x="0.0" y="0.0" z="0.0"/>
          <localScale x="1.0" y="1.0" z="1.0"/>
          <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
          <geometry class="net.untoldwind.moredread.model.mesh.QuadMesh">
            <numVertices>8</numVertices>
            <vertex x="-0.9" y="-0.8" z="-0.7"/>
            <vertex x="1.1" y="-0.8" z="-0.7"/>
            <vertex x="1.1" y="1.2" z="-0.7"/>
            <vertex x="-0.9" y="1.2" z="-0.7"/>
            <vertex x="-0.9" y="-0.8" z="1.3"/>
            <vertex x="1.1" y="-0.8" z="1.3"/>
            <vertex x="1.1" y="1.2" z="1.3"/>
            <vertex x="-0.9" y="1.2" z="1.3"/>
            <numFaces>6</numFaces>
            <index>3</index>
            <index>2</index>
            <index>1</index>
            <index>0</index>
            <index>4</index>
            <index>5</index>
            <index>6</index>
            <index>7</index>
            <index>4</index>
            <index>7</index>
            <index>3</index>
            <index>0</index>
            <index>1</index>
            <index>2</index>
            <index>6</index>
            <index>5</index>
            <index>0</index>
            <index>1</index>
            <index>5</index>
            <index>4</index>
            <index>2</index>
            <index>3</index>
            <index>7</index>
            <index>6</index>
          </geometry>
        </element>
      </children>
    </element>
    <element class="net.untoldwind.moredread.model.scene.MeshNode">
      <name>Mesh Icosahedron</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-20.0" y="10.0" z="0.0"/>
      <localScale x="3.0" y="3.0" z="3.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.TriangleMesh">
        <numVertices>12</numVertices>
        <vertex x="0.8506508" y="0.5257311" z="0.0"/>
        <vertex x="-0.8506508" y="0.5257311" z="0.0"/>
        <vertex x="0.8506508" y="-0.5257311" z="0.0"/>
        <vertex x="-0.8506508" y="-0.5257311" z="0.0"/>
        <vertex x="0.5257311" y="0.0" z="0.8506508"/>
        <vertex x="0.5257311" y="0.0" z="-0.8506508"/>
        <vertex x="-0.5257311" y="0.0" z="0.8506508"/>
        <vertex x="-0.5257311" y="0.0" z="-0.8506508"/>
        <vertex x="0.0" y="0.8506508" z="0.5257311"/>
        <vertex x="0.0" y="-0.8506508" z="0.5257311"/>
        <vertex x="0.0" y="0.8506508" z="-0.5257311"/>
        <vertex x="0.0" y="-0.8506508" z="-0.5257311"/>
        <numFaces>20</numFaces>
        <index>0</index>
        <index>8</index>
        <index>4</index>
        <index>0</index>
        <index>5</index>
        <index>10</index>
        <index>2</index>
        <index>4</index>
        <index>9</index>
        <index>2</index>
        <index>11</index>
        <index>5</index>
        <index>1</index>
        <index>6</index>
        <index>8</index>
        <index>1</index>
        <index>10</index>
        <index>7</index>
        <index>3</index>
        <index>9</index>
        <index>6</index>
        <index>3</index>
        <index>7</index>
        <index>11</index>
        <index>0</index>
        <index>10</index>
        <index>8</index>
        <index>1</index>
        <index>8</index>
        <index>10</index>
        <index>2</index>
        <index>9</index>
        <index>11</index>
        <index>3</index>
        <index>11</index>
        <index>9</index>
        <index>4</index>
        <index>2</index>
        <index>0</index>
        <index>5</index>
        <index>0</index>
        <index>2</index>
        <index>6</index>
        <index>1</index>
        <index>3</index>
        <index>7</index>
        <index>3</index>
        <index>1</index>
        <index>8</index>
        <index>6</index>
        <index>4</index>
        <index>9</index>
        <index>4</index>
        <index>6</index>
        <index>10</index>
        <index>5</index>
        <index>7</index>
        <index>11</index>
        <index>7</index>
        <index>5</index>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.MeshNode">
      <name>Mesh Geosphere</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="-30.0" y="10.0" z="0.0"/>
      <localScale x="5.0" y="5.0" z="5.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.TriangleMesh">
        <numVertices>312</numVertices>
        <vertex x="0.8506508" y="0.5257311" z="0.0"/>
        <vertex x="-0.8506508" y="0.5257311" z="0.0"/>
        <vertex x="0.8506508" y="-0.5257311" z="0.0"/>
        <vertex x="-0.8506508" y="-0.5257311" z="0.0"/>
        <vertex x="0.5257311" y="0.0" z="0.8506508"/>
        <vertex x="0.5257311" y="0.0" z="-0.8506508"/>
        <vertex x="-0.5257311" y="0.0" z="0.8506508"/>
        <vertex x="-0.5257311" y="0.0" z="-0.8506508"/>
        <vertex x="0.0" y="0.8506508" z="0.5257311"/>
        <vertex x="0.0" y="-0.8506508" z="0.5257311"/>
        <vertex x="0.0" y="0.8506508" z="-0.5257311"/>
        <vertex x="0.0" y="-0.8506508" z="-0.5257311"/>
        <vertex x="0.80901694" y="0.30901697" z="0.49999997"/>
        <vertex x="0.49999997" y="0.80901694" z="0.30901697"/>
        <vertex x="0.30901697" y="0.49999997" z="0.80901694"/>
        <vertex x="0.49999997" y="0.80901694" z="-0.30901697"/>
        <vertex x="0.80901694" y="0.30901697" z="-0.49999997"/>
        <vertex x="0.30901697" y="0.49999997" z="-0.80901694"/>
        <vertex x="0.49999997" y="-0.80901694" z="0.30901697"/>
        <vertex x="0.80901694" y="-0.30901697" z="0.49999997"/>
        <vertex x="0.30901697" y="-0.49999997" z="0.80901694"/>
        <vertex x="0.80901694" y="-0.30901697" z="-0.49999997"/>
        <vertex x="0.49999997" y="-0.80901694" z="-0.30901697"/>
        <vertex x="0.30901697" y="-0.49999997" z="-0.80901694"/>
        <vertex x="-0.49999997" y="0.80901694" z="0.30901697"/>
        <vertex x="-0.80901694" y="0.30901697" z="0.49999997"/>
        <vertex x="-0.30901697" y="0.49999997" z="0.80901694"/>
        <vertex x="-0.80901694" y="0.30901697" z="-0.49999997"/>
        <vertex x="-0.49999997" y="0.80901694" z="-0.30901697"/>
        <vertex x="-0.30901697" y="0.49999997" z="-0.80901694"/>
        <vertex x="-0.80901694" y="-0.30901697" z="0.49999997"/>
        <vertex x="-0.49999997" y="-0.80901694" z="0.30901697"/>
        <vertex x="-0.30901697" y="-0.49999997" z="0.80901694"/>
        <vertex x="-0.49999997" y="-0.80901694" z="-0.30901697"/>
        <vertex x="-0.80901694" y="-0.30901697" z="-0.49999997"/>
        <vertex x="-0.30901697" y="-0.49999997" z="-0.80901694"/>
        <vertex x="0.49999997" y="0.80901694" z="0.30901697"/>
        <vertex x="0.49999997" y="0.80901694" z="-0.30901697"/>
        <vertex x="0.0" y="0.99999994" z="0.0"/>
        <vertex x="-0.49999997" y="0.80901694" z="-0.30901697"/>
        <vertex x="-0.49999997" y="0.80901694" z="0.30901697"/>
        <vertex x="0.0" y="0.99999994" z="0.0"/>
        <vertex x="0.49999997" y="-0.80901694" z="-0.30901697"/>
        <vertex x="0.49999997" y="-0.80901694" z="0.30901697"/>
        <vertex x="0.0" y="-0.99999994" z="0.0"/>
        <vertex x="-0.49999997" y="-0.80901694" z="0.30901697"/>
        <vertex x="-0.49999997" y="-0.80901694" z="-0.30901697"/>
        <vertex x="0.0" y="-0.99999994" z="0.0"/>
        <vertex x="0.80901694" y="0.30901697" z="0.49999997"/>
        <vertex x="0.80901694" y="-0.30901697" z="0.49999997"/>
        <vertex x="0.99999994" y="0.0" z="0.0"/>
        <vertex x="0.80901694" y="-0.30901697" z="-0.49999997"/>
        <vertex x="0.80901694" y="0.30901697" z="-0.49999997"/>
        <vertex x="0.99999994" y="0.0" z="0.0"/>
        <vertex x="-0.80901694" y="-0.30901697" z="0.49999997"/>
        <vertex x="-0.80901694" y="0.30901697" z="0.49999997"/>
        <vertex x="-0.99999994" y="0.0" z="0.0"/>
        <vertex x="-0.80901694" y="0.30901697" z="-0.49999997"/>
        <vertex x="-0.80901694" y="-0.30901697" z="-0.49999997"/>
        <vertex x="-0.99999994" y="0.0" z="0.0"/>
        <vertex x="0.30901697" y="0.49999997" z="0.80901694"/>
        <vertex x="-0.30901697" y="0.49999997" z="0.80901694"/>
        <vertex x="0.0" y="0.0" z="0.99999994"/>
        <vertex x="-0.30901697" y="-0.49999997" z="0.80901694"/>
        <vertex x="0.30901697" y="-0.49999997" z="0.80901694"/>
        <vertex x="0.0" y="0.0" z="0.99999994"/>
        <vertex x="-0.30901697" y="0.49999997" z="-0.80901694"/>
        <vertex x="0.30901697" y="0.49999997" z="-0.80901694"/>
        <vertex x="0.0" y="0.0" z="-0.99999994"/>
        <vertex x="0.30901697" y="-0.49999997" z="-0.80901694"/>
        <vertex x="-0.30901697" y="-0.49999997" z="-0.80901694"/>
        <vertex x="0.0" y="0.0" z="-0.99999994"/>
        <vertex x="0.8626685" y="0.43388855" z="0.2598919"/>
        <vertex x="0.70204645" y="0.6937805" z="0.16062203"/>
        <vertex x="0.688191" y="0.58778524" z="0.4253254"/>
        <vertex x="0.4253254" y="0.688191" z="0.58778524"/>
        <vertex x="0.2598919" y="0.8626685" z="0.43388855"/>
        <vertex x="0.16062203" y="0.70204645" z="0.6937805"/>
        <vertex x="0.58778524" y="0.4253254" z="0.688191"/>
        <vertex x="0.688191" y="0.58778524" z="0.4253254"/>
        <vertex x="0.4253254" y="0.688191" z="0.58778524"/>
        <vertex x="0.6937805" y="0.16062203" z="0.70204645"/>
        <vertex x="0.58778524" y="0.4253254" z="0.688191"/>
        <vertex x="0.43388855" y="0.2598919" z="0.8626685"/>
        <vertex x="0.70204645" y="0.6937805" z="-0.16062203"/>
        <vertex x="0.8626685" y="0.43388855" z="-0.2598919"/>
        <vertex x="0.688191" y="0.58778524" z="-0.4253254"/>
        <vertex x="0.58778524" y="0.4253254" z="-0.688191"/>
        <vertex x="0.6937805" y="0.16062203" z="-0.70204645"/>
        <vertex x="0.43388855" y="0.2598919" z="-0.8626685"/>
        <vertex x="0.4253254" y="0.688191" z="-0.58778524"/>
        <vertex x="0.688191" y="0.58778524" z="-0.4253254"/>
        <vertex x="0.58778524" y="0.4253254" z="-0.688191"/>
        <vertex x="0.2598919" y="0.8626685" z="-0.43388855"/>
        <vertex x="0.4253254" y="0.688191" z="-0.58778524"/>
        <vertex x="0.16062203" y="0.70204645" z="-0.6937805"/>
        <vertex x="0.70204645" y="-0.6937805" z="0.16062203"/>
        <vertex x="0.8626685" y="-0.43388855" z="0.2598919"/>
        <vertex x="0.688191" y="-0.58778524" z="0.4253254"/>
        <vertex x="0.58778524" y="-0.4253254" z="0.688191"/>
        <vertex x="0.6937805" y="-0.16062203" z="0.70204645"/>
        <vertex x="0.43388855" y="-0.2598919" z="0.8626685"/>
        <vertex x="0.4253254" y="-0.688191" z="0.58778524"/>
        <vertex x="0.688191" y="-0.58778524" z="0.4253254"/>
        <vertex x="0.58778524" y="-0.4253254" z="0.688191"/>
        <vertex x="0.2598919" y="-0.8626685" z="0.43388855"/>
        <vertex x="0.4253254" y="-0.688191" z="0.58778524"/>
        <vertex x="0.16062203" y="-0.70204645" z="0.6937805"/>
        <vertex x="0.8626685" y="-0.43388855" z="-0.2598919"/>
        <vertex x="0.70204645" y="-0.6937805" z="-0.16062203"/>
        <vertex x="0.688191" y="-0.58778524" z="-0.4253254"/>
        <vertex x="0.4253254" y="-0.688191" z="-0.58778524"/>
        <vertex x="0.2598919" y="-0.8626685" z="-0.43388855"/>
        <vertex x="0.16062203" y="-0.70204645" z="-0.6937805"/>
        <vertex x="0.58778524" y="-0.4253254" z="-0.688191"/>
        <vertex x="0.688191" y="-0.58778524" z="-0.4253254"/>
        <vertex x="0.4253254" y="-0.688191" z="-0.58778524"/>
        <vertex x="0.6937805" y="-0.16062203" z="-0.70204645"/>
        <vertex x="0.58778524" y="-0.4253254" z="-0.688191"/>
        <vertex x="0.43388855" y="-0.2598919" z="-0.8626685"/>
        <vertex x="-0.70204645" y="0.6937805" z="0.16062203"/>
        <vertex x="-0.8626685" y="0.43388855" z="0.2598919"/>
        <vertex x="-0.688191" y="0.58778524" z="0.4253254"/>
        <vertex x="-0.58778524" y="0.4253254" z="0.688191"/>
        <vertex x="-0.6937805" y="0.16062203" z="0.70204645"/>
        <vertex x="-0.43388855" y="0.2598919" z="0.8626685"/>
        <vertex x="-0.4253254" y="0.688191" z="0.58778524"/>
        <vertex x="-0.688191" y="0.58778524" z="0.4253254"/>
        <vertex x="-0.58778524" y="0.4253254" z="0.688191"/>
        <vertex x="-0.2598919" y="0.8626685" z="0.43388855"/>
        <vertex x="-0.4253254" y="0.688191" z="0.58778524"/>
        <vertex x="-0.16062203" y="0.70204645" z="0.6937805"/>
        <vertex x="-0.8626685" y="0.43388855" z="-0.2598919"/>
        <vertex x="-0.70204645" y="0.6937805" z="-0.16062203"/>
        <vertex x="-0.688191" y="0.58778524" z="-0.4253254"/>
        <vertex x="-0.4253254" y="0.688191" z="-0.58778524"/>
        <vertex x="-0.2598919" y="0.8626685" z="-0.43388855"/>
        <vertex x="-0.16062203" y="0.70204645" z="-0.6937805"/>
        <vertex x="-0.58778524" y="0.4253254" z="-0.688191"/>
        <vertex x="-0.688191" y="0.58778524" z="-0.4253254"/>
        <vertex x="-0.4253254" y="0.688191" z="-0.58778524"/>
        <vertex x="-0.6937805" y="0.16062203" z="-0.70204645"/>
        <vertex x="-0.58778524" y="0.4253254" z="-0.688191"/>
        <vertex x="-0.43388855" y="0.2598919" z="-0.8626685"/>
        <vertex x="-0.8626685" y="-0.43388855" z="0.2598919"/>
        <vertex x="-0.70204645" y="-0.6937805" z="0.16062203"/>
        <vertex x="-0.688191" y="-0.58778524" z="0.4253254"/>
        <vertex x="-0.4253254" y="-0.688191" z="0.58778524"/>
        <vertex x="-0.2598919" y="-0.8626685" z="0.43388855"/>
        <vertex x="-0.16062203" y="-0.70204645" z="0.6937805"/>
        <vertex x="-0.58778524" y="-0.4253254" z="0.688191"/>
        <vertex x="-0.688191" y="-0.58778524" z="0.4253254"/>
        <vertex x="-0.4253254" y="-0.688191" z="0.58778524"/>
        <vertex x="-0.6937805" y="-0.16062203" z="0.70204645"/>
        <vertex x="-0.58778524" y="-0.4253254" z="0.688191"/>
        <vertex x="-0.43388855" y="-0.2598919" z="0.8626685"/>
        <vertex x="-0.70204645" y="-0.6937805" z="-0.16062203"/>
        <vertex x="-0.8626685" y="-0.43388855" z="-0.2598919"/>
        <vertex x="-0.688191" y="-0.58778524" z="-0.4253254"/>
        <vertex x="-0.58778524" y="-0.4253254" z="-0.688191"/>
        <vertex x="-0.6937805" y="-0.16062203" z="-0.70204645"/>
        <vertex x="-0.43388855" y="-0.2598919" z="-0.8626685"/>
        <vertex x="-0.4253254" y="-0.688191" z="-0.58778524"/>
        <vertex x="-0.688191" y="-0.58778524" z="-0.4253254"/>
        <vertex x="-0.58778524" y="-0.4253254" z="-0.688191"/>
        <vertex x="-0.2598919" y="-0.8626685" z="-0.43388855"/>
        <vertex x="-0.4253254" y="-0.688191" z="-0.58778524"/>
        <vertex x="-0.16062203" y="-0.70204645" z="-0.6937805"/>
        <vertex x="0.70204645" y="0.6937805" z="0.16062203"/>
        <vertex x="0.70204645" y="0.6937805" z="-0.16062203"/>
        <vertex x="0.5257311" y="0.8506508" z="0.0"/>
        <vertex x="0.26286554" y="0.95105654" z="-0.16245985"/>
        <vertex x="0.2598919" y="0.8626685" z="-0.43388855"/>
        <vertex x="0.0" y="0.96193826" z="-0.2732665"/>
        <vertex x="0.26286554" y="0.95105654" z="0.16245985"/>
        <vertex x="0.5257311" y="0.8506508" z="0.0"/>
        <vertex x="0.26286554" y="0.95105654" z="-0.16245985"/>
        <vertex x="0.2598919" y="0.8626685" z="0.43388855"/>
        <vertex x="0.26286554" y="0.95105654" z="0.16245985"/>
        <vertex x="0.0" y="0.96193826" z="0.2732665"/>
        <vertex x="-0.70204645" y="0.6937805" z="-0.16062203"/>
        <vertex x="-0.70204645" y="0.6937805" z="0.16062203"/>
        <vertex x="-0.5257311" y="0.8506508" z="0.0"/>
        <vertex x="-0.26286554" y="0.95105654" z="0.16245985"/>
        <vertex x="-0.2598919" y="0.8626685" z="0.43388855"/>
        <vertex x="0.0" y="0.96193826" z="0.2732665"/>
        <vertex x="-0.26286554" y="0.95105654" z="-0.16245985"/>
        <vertex x="-0.5257311" y="0.8506508" z="0.0"/>
        <vertex x="-0.26286554" y="0.95105654" z="0.16245985"/>
        <vertex x="-0.2598919" y="0.8626685" z="-0.43388855"/>
        <vertex x="-0.26286554" y="0.95105654" z="-0.16245985"/>
        <vertex x="0.0" y="0.96193826" z="-0.2732665"/>
        <vertex x="0.70204645" y="-0.6937805" z="-0.16062203"/>
        <vertex x="0.70204645" y="-0.6937805" z="0.16062203"/>
        <vertex x="0.5257311" y="-0.8506508" z="0.0"/>
        <vertex x="0.26286554" y="-0.95105654" z="0.16245985"/>
        <vertex x="0.2598919" y="-0.8626685" z="0.43388855"/>
        <vertex x="0.0" y="-0.96193826" z="0.2732665"/>
        <vertex x="0.26286554" y="-0.95105654" z="-0.16245985"/>
        <vertex x="0.5257311" y="-0.8506508" z="0.0"/>
        <vertex x="0.26286554" y="-0.95105654" z="0.16245985"/>
        <vertex x="0.2598919" y="-0.8626685" z="-0.43388855"/>
        <vertex x="0.26286554" y="-0.95105654" z="-0.16245985"/>
        <vertex x="0.0" y="-0.96193826" z="-0.2732665"/>
        <vertex x="-0.70204645" y="-0.6937805" z="0.16062203"/>
        <vertex x="-0.70204645" y="-0.6937805" z="-0.16062203"/>
        <vertex x="-0.5257311" y="-0.8506508" z="0.0"/>
        <vertex x="-0.26286554" y="-0.95105654" z="-0.16245985"/>
        <vertex x="-0.2598919" y="-0.8626685" z="-0.43388855"/>
        <vertex x="0.0" y="-0.96193826" z="-0.2732665"/>
        <vertex x="-0.26286554" y="-0.95105654" z="0.16245985"/>
        <vertex x="-0.5257311" y="-0.8506508" z="0.0"/>
        <vertex x="-0.26286554" y="-0.95105654" z="-0.16245985"/>
        <vertex x="-0.2598919" y="-0.8626685" z="0.43388855"/>
        <vertex x="-0.26286554" y="-0.95105654" z="0.16245985"/>
        <vertex x="0.0" y="-0.96193826" z="0.2732665"/>
        <vertex x="0.6937805" y="0.16062203" z="0.70204645"/>
        <vertex x="0.6937805" y="-0.16062203" z="0.70204645"/>
        <vertex x="0.8506508" y="0.0" z="0.5257311"/>
        <vertex x="0.95105654" y="-0.16245985" z="0.26286554"/>
        <vertex x="0.8626685" y="-0.43388855" z="0.2598919"/>
        <vertex x="0.96193826" y="-0.2732665" z="0.0"/>
        <vertex x="0.95105654" y="0.16245985" z="0.26286554"/>
        <vertex x="0.8506508" y="0.0" z="0.5257311"/>
        <vertex x="0.95105654" y="-0.16245985" z="0.26286554"/>
        <vertex x="0.8626685" y="0.43388855" z="0.2598919"/>
        <vertex x="0.95105654" y="0.16245985" z="0.26286554"/>
        <vertex x="0.96193826" y="0.2732665" z="0.0"/>
        <vertex x="0.6937805" y="-0.16062203" z="-0.70204645"/>
        <vertex x="0.6937805" y="0.16062203" z="-0.70204645"/>
        <vertex x="0.8506508" y="0.0" z="-0.5257311"/>
        <vertex x="0.95105654" y="0.16245985" z="-0.26286554"/>
        <vertex x="0.8626685" y="0.43388855" z="-0.2598919"/>
        <vertex x="0.96193826" y="0.2732665" z="0.0"/>
        <vertex x="0.95105654" y="-0.16245985" z="-0.26286554"/>
        <vertex x="0.8506508" y="0.0" z="-0.5257311"/>
        <vertex x="0.95105654" y="0.16245985" z="-0.26286554"/>
        <vertex x="0.8626685" y="-0.43388855" z="-0.2598919"/>
        <vertex x="0.95105654" y="-0.16245985" z="-0.26286554"/>
        <vertex x="0.96193826" y="-0.2732665" z="0.0"/>
        <vertex x="-0.6937805" y="-0.16062203" z="0.70204645"/>
        <vertex x="-0.6937805" y="0.16062203" z="0.70204645"/>
        <vertex x="-0.8506508" y="0.0" z="0.5257311"/>
        <vertex x="-0.95105654" y="0.16245985" z="0.26286554"/>
        <vertex x="-0.8626685" y="0.43388855" z="0.2598919"/>
        <vertex x="-0.96193826" y="0.2732665" z="0.0"/>
        <vertex x="-0.95105654" y="-0.16245985" z="0.26286554"/>
        <vertex x="-0.8506508" y="0.0" z="0.5257311"/>
        <vertex x="-0.95105654" y="0.16245985" z="0.26286554"/>
        <vertex x="-0.8626685" y="-0.43388855" z="0.2598919"/>
        <vertex x="-0.95105654" y="-0.16245985" z="0.26286554"/>
        <vertex x="-0.96193826" y="-0.2732665" z="0.0"/>
        <vertex x="-0.6937805" y="0.16062203" z="-0.70204645"/>
        <vertex x="-0.6937805" y="-0.16062203" z="-0.70204645"/>
        <vertex x="-0.8506508" y="0.0" z="-0.5257311"/>
        <vertex x="-0.95105654" y="-0.16245985" z="-0.26286554"/>
        <vertex x="-0.8626685" y="-0.43388855" z="-0.2598919"/>
        <vertex x="-0.96193826" y="-0.2732665" z="0.0"/>
        <vertex x="-0.95105654" y="0.16245985" z="-0.26286554"/>
        <vertex x="-0.8506508" y="0.0" z="-0.5257311"/>
        <vertex x="-0.95105654" y="-0.16245985" z="-0.26286554"/>
        <vertex x="-0.8626685" y="0.43388855" z="-0.2598919"/>
        <vertex x="-0.95105654" y="0.16245985" z="-0.26286554"/>
        <vertex x="-0.96193826" y="0.2732665" z="0.0"/>
        <vertex x="0.16062203" y="0.70204645" z="0.6937805"/>
        <vertex x="-0.16062203" y="0.70204645" z="0.6937805"/>
        <vertex x="0.0" y="0.5257311" z="0.8506508"/>
        <vertex x="-0.16245985" y="0.26286554" z="0.95105654"/>
        <vertex x="-0.43388855" y="0.2598919" z="0.8626685"/>
        <vertex x="-0.2732665" y="0.0" z="0.96193826"/>
        <vertex x="0.16245985" y="0.26286554" z="0.95105654"/>
        <vertex x="0.0" y="0.5257311" z="0.8506508"/>
        <vertex x="-0.16245985" y="0.26286554" z="0.95105654"/>
        <vertex x="0.43388855" y="0.2598919" z="0.8626685"/>
        <vertex x="0.16245985" y="0.26286554" z="0.95105654"/>
        <vertex x="0.2732665" y="0.0" z="0.96193826"/>
        <vertex x="-0.16062203" y="-0.70204645" z="0.6937805"/>
        <vertex x="0.16062203" y="-0.70204645" z="0.6937805"/>
        <vertex x="0.0" y="-0.5257311" z="0.8506508"/>
        <vertex x="0.16245985" y="-0.26286554" z="0.95105654"/>
        <vertex x="0.43388855" y="-0.2598919" z="0.8626685"/>
        <vertex x="0.2732665" y="0.0" z="0.96193826"/>
        <vertex x="-0.16245985" y="-0.26286554" z="0.95105654"/>
        <vertex x="0.0" y="-0.5257311" z="0.8506508"/>
        <vertex x="0.16245985" y="-0.26286554" z="0.95105654"/>
        <vertex x="-0.43388855" y="-0.2598919" z="0.8626685"/>
        <vertex x="-0.16245985" y="-0.26286554" z="0.95105654"/>
        <vertex x="-0.2732665" y="0.0" z="0.96193826"/>
        <vertex x="-0.16062203" y="0.70204645" z="-0.6937805"/>
        <vertex x="0.16062203" y="0.70204645" z="-0.6937805"/>
        <vertex x="0.0" y="0.5257311" z="-0.8506508"/>
        <vertex x="0.16245985" y="0.26286554" z="-0.95105654"/>
        <vertex x="0.43388855" y="0.2598919" z="-0.8626685"/>
        <vertex x="0.2732665" y="0.0" z="-0.96193826"/>
        <vertex x="-0.16245985" y="0.26286554" z="-0.95105654"/>
        <vertex x="0.0" y="0.5257311" z="-0.8506508"/>
        <vertex x="0.16245985" y="0.26286554" z="-0.95105654"/>
        <vertex x="-0.43388855" y="0.2598919" z="-0.8626685"/>
        <vertex x="-0.16245985" y="0.26286554" z="-0.95105654"/>
        <vertex x="-0.2732665" y="0.0" z="-0.96193826"/>
        <vertex x="0.16062203" y="-0.70204645" z="-0.6937805"/>
        <vertex x="-0.16062203" y="-0.70204645" z="-0.6937805"/>
        <vertex x="0.0" y="-0.5257311" z="-0.8506508"/>
        <vertex x="-0.16245985" y="-0.26286554" z="-0.95105654"/>
        <vertex x="-0.43388855" y="-0.2598919" z="-0.8626685"/>
        <vertex x="-0.2732665" y="0.0" z="-0.96193826"/>
        <vertex x="0.16245985" y="-0.26286554" z="-0.95105654"/>
        <vertex x="0.0" y="-0.5257311" z="-0.8506508"/>
        <vertex x="-0.16245985" y="-0.26286554" z="-0.95105654"/>
        <vertex x="0.43388855" y="-0.2598919" z="-0.8626685"/>
        <vertex x="0.16245985" y="-0.26286554" z="-0.95105654"/>
        <vertex x="0.2732665" y="0.0" z="-0.96193826"/>
        <numFaces>320</numFaces>
        <index>0</index>
        <index>73</index>
        <index>72</index>
        <index>73</index>
        <index>13</index>
        <index>74</index>
        <index>72</index>
        <index>73</index>
        <index>74</index>
        <index>72</index>
        <index>74</index>
        <index>12</index>
        <index>13</index>
        <index>76</index>
        <index>75</index>
        <index>76</index>
        <index>8</index>
        <index>77</index>
        <index>75</index>
        <index>76</index>
        <index>77</index>
        <index>75</index>
        <index>77</index>
        <index>14</index>
        <index>12</index>
        <index>79</index>
        <index>78</index>
        <index>79</index>
        <index>13</index>
        <index>80</index>
        <index>78</index>
        <index>79</index>
        <index>80</index>
        <index>78</index>
        <index>80</index>
        <index>14</index>
        <index>12</index>
        <index>82</index>
        <index>81</index>
        <index>82</index>
        <index>14</index>
        <index>83</index>
        <index>81</index>
        <index>82</index>
        <index>83</index>
        <index>81</index>
        <index>83</index>
        <index>4</index>
        <index>0</index>
        <index>85</index>
        <index>84</index>
        <index>85</index>
        <index>16</index>
        <index>86</index>
        <index>84</index>
        <index>85</index>
        <index>86</index>
        <index>84</index>
        <index>86</index>
        <index>15</index>
        <index>16</index>
        <index>88</index>
        <index>87</index>
        <index>88</index>
        <index>5</index>
        <index>89</index>
        <index>87</index>
        <index>88</index>
        <index>89</index>
        <index>87</index>
        <index>89</index>
        <index>17</index>
        <index>15</index>
        <index>91</index>
        <index>90</index>
        <index>91</index>
        <index>16</index>
        <index>92</index>
        <index>90</index>
        <index>91</index>
        <index>92</index>
        <index>90</index>
        <index>92</index>
        <index>17</index>
        <index>15</index>
        <index>94</index>
        <index>93</index>
        <index>94</index>
        <index>17</index>
        <index>95</index>
        <index>93</index>
        <index>94</index>
        <index>95</index>
        <index>93</index>
        <index>95</index>
        <index>10</index>
        <index>2</index>
        <index>97</index>
        <index>96</index>
        <index>97</index>
        <index>19</index>
        <index>98</index>
        <index>96</index>
        <index>97</index>
        <index>98</index>
        <index>96</index>
        <index>98</index>
        <index>18</index>
        <index>19</index>
        <index>100</index>
        <index>99</index>
        <index>100</index>
        <index>4</index>
        <index>101</index>
        <index>99</index>
        <index>100</index>
        <index>101</index>
        <index>99</index>
        <index>101</index>
        <index>20</index>
        <index>18</index>
        <index>103</index>
        <index>102</index>
        <index>103</index>
        <index>19</index>
        <index>104</index>
        <index>102</index>
        <index>103</index>
        <index>104</index>
        <index>102</index>
        <index>104</index>
        <index>20</index>
        <index>18</index>
        <index>106</index>
        <index>105</index>
        <index>106</index>
        <index>20</index>
        <index>107</index>
        <index>105</index>
        <index>106</index>
        <index>107</index>
        <index>105</index>
        <index>107</index>
        <index>9</index>
        <index>2</index>
        <index>109</index>
        <index>108</index>
        <index>109</index>
        <index>22</index>
        <index>110</index>
        <index>108</index>
        <index>109</index>
        <index>110</index>
        <index>108</index>
        <index>110</index>
        <index>21</index>
        <index>22</index>
        <index>112</index>
        <index>111</index>
        <index>112</index>
        <index>11</index>
        <index>113</index>
        <index>111</index>
        <index>112</index>
        <index>113</index>
        <index>111</index>
        <index>113</index>
        <index>23</index>
        <index>21</index>
        <index>115</index>
        <index>114</index>
        <index>115</index>
        <index>22</index>
        <index>116</index>
        <index>114</index>
        <index>115</index>
        <index>116</index>
        <index>114</index>
        <index>116</index>
        <index>23</index>
        <index>21</index>
        <index>118</index>
        <index>117</index>
        <index>118</index>
        <index>23</index>
        <index>119</index>
        <index>117</index>
        <index>118</index>
        <index>119</index>
        <index>117</index>
        <index>119</index>
        <index>5</index>
        <index>1</index>
        <index>121</index>
        <index>120</index>
        <index>121</index>
        <index>25</index>
        <index>122</index>
        <index>120</index>
        <index>121</index>
        <index>122</index>
        <index>120</index>
        <index>122</index>
        <index>24</index>
        <index>25</index>
        <index>124</index>
        <index>123</index>
        <index>124</index>
        <index>6</index>
        <index>125</index>
        <index>123</index>
        <index>124</index>
        <index>125</index>
        <index>123</index>
        <index>125</index>
        <index>26</index>
        <index>24</index>
        <index>127</index>
        <index>126</index>
        <index>127</index>
        <index>25</index>
        <index>128</index>
        <index>126</index>
        <index>127</index>
        <index>128</index>
        <index>126</index>
        <index>128</index>
        <index>26</index>
        <index>24</index>
        <index>130</index>
        <index>129</index>
        <index>130</index>
        <index>26</index>
        <index>131</index>
        <index>129</index>
        <index>130</index>
        <index>131</index>
        <index>129</index>
        <index>131</index>
        <index>8</index>
        <index>1</index>
        <index>133</index>
        <index>132</index>
        <index>133</index>
        <index>28</index>
        <index>134</index>
        <index>132</index>
        <index>133</index>
        <index>134</index>
        <index>132</index>
        <index>134</index>
        <index>27</index>
        <index>28</index>
        <index>136</index>
        <index>135</index>
        <index>136</index>
        <index>10</index>
        <index>137</index>
        <index>135</index>
        <index>136</index>
        <index>137</index>
        <index>135</index>
        <index>137</index>
        <index>29</index>
        <index>27</index>
        <index>139</index>
        <index>138</index>
        <index>139</index>
        <index>28</index>
        <index>140</index>
        <index>138</index>
        <index>139</index>
        <index>140</index>
        <index>138</index>
        <index>140</index>
        <index>29</index>
        <index>27</index>
        <index>142</index>
        <index>141</index>
        <index>142</index>
        <index>29</index>
        <index>143</index>
        <index>141</index>
        <index>142</index>
        <index>143</index>
        <index>141</index>
        <index>143</index>
        <index>7</index>
        <index>3</index>
        <index>145</index>
        <index>144</index>
        <index>145</index>
        <index>31</index>
        <index>146</index>
        <index>144</index>
        <index>145</index>
        <index>146</index>
        <index>144</index>
        <index>146</index>
        <index>30</index>
        <index>31</index>
        <index>148</index>
        <index>147</index>
        <index>148</index>
        <index>9</index>
        <index>149</index>
        <index>147</index>
        <index>148</index>
        <index>149</index>
        <index>147</index>
        <index>149</index>
        <index>32</index>
        <index>30</index>
        <index>151</index>
        <index>150</index>
        <index>151</index>
        <index>31</index>
        <index>152</index>
        <index>150</index>
        <index>151</index>
        <index>152</index>
        <index>150</index>
        <index>152</index>
        <index>32</index>
        <index>30</index>
        <index>154</index>
        <index>153</index>
        <index>154</index>
        <index>32</index>
        <index>155</index>
        <index>153</index>
        <index>154</index>
        <index>155</index>
        <index>153</index>
        <index>155</index>
        <index>6</index>
        <index>3</index>
        <index>157</index>
        <index>156</index>
        <index>157</index>
        <index>34</index>
        <index>158</index>
        <index>156</index>
        <index>157</index>
        <index>158</index>
        <index>156</index>
        <index>158</index>
        <index>33</index>
        <index>34</index>
        <index>160</index>
        <index>159</index>
        <index>160</index>
        <index>7</index>
        <index>161</index>
        <index>159</index>
        <index>160</index>
        <index>161</index>
        <index>159</index>
        <index>161</index>
        <index>35</index>
        <index>33</index>
        <index>163</index>
        <index>162</index>
        <index>163</index>
        <index>34</index>
        <index>164</index>
        <index>162</index>
        <index>163</index>
        <index>164</index>
        <index>162</index>
        <index>164</index>
        <index>35</index>
        <index>33</index>
        <index>166</index>
        <index>165</index>
        <index>166</index>
        <index>35</index>
        <index>167</index>
        <index>165</index>
        <index>166</index>
        <index>167</index>
        <index>165</index>
        <index>167</index>
        <index>11</index>
        <index>0</index>
        <index>169</index>
        <index>168</index>
        <index>169</index>
        <index>37</index>
        <index>170</index>
        <index>168</index>
        <index>169</index>
        <index>170</index>
        <index>168</index>
        <index>170</index>
        <index>36</index>
        <index>37</index>
        <index>172</index>
        <index>171</index>
        <index>172</index>
        <index>10</index>
        <index>173</index>
        <index>171</index>
        <index>172</index>
        <index>173</index>
        <index>171</index>
        <index>173</index>
        <index>38</index>
        <index>36</index>
        <index>175</index>
        <index>174</index>
        <index>175</index>
        <index>37</index>
        <index>176</index>
        <index>174</index>
        <index>175</index>
        <index>176</index>
        <index>174</index>
        <index>176</index>
        <index>38</index>
        <index>36</index>
        <index>178</index>
        <index>177</index>
        <index>178</index>
        <index>38</index>
        <index>179</index>
        <index>177</index>
        <index>178</index>
        <index>179</index>
        <index>177</index>
        <index>179</index>
        <index>8</index>
        <index>1</index>
        <index>181</index>
        <index>180</index>
        <index>181</index>
        <index>40</index>
        <index>182</index>
        <index>180</index>
        <index>181</index>
        <index>182</index>
        <index>180</index>
        <index>182</index>
        <index>39</index>
        <index>40</index>
        <index>184</index>
        <index>183</index>
        <index>184</index>
        <index>8</index>
        <index>185</index>
        <index>183</index>
        <index>184</index>
        <index>185</index>
        <index>183</index>
        <index>185</index>
        <index>41</index>
        <index>39</index>
        <index>187</index>
        <index>186</index>
        <index>187</index>
        <index>40</index>
        <index>188</index>
        <index>186</index>
        <index>187</index>
        <index>188</index>
        <index>186</index>
        <index>188</index>
        <index>41</index>
        <index>39</index>
        <index>190</index>
        <index>189</index>
        <index>190</index>
        <index>41</index>
        <index>191</index>
        <index>189</index>
        <index>190</index>
        <index>191</index>
        <index>189</index>
        <index>191</index>
        <index>10</index>
        <index>2</index>
        <index>193</index>
        <index>192</index>
        <index>193</index>
        <index>43</index>
        <index>194</index>
        <index>192</index>
        <index>193</index>
        <index>194</index>
        <index>192</index>
        <index>194</index>
        <index>42</index>
        <index>43</index>
        <index>196</index>
        <index>195</index>
        <index>196</index>
        <index>9</index>
        <index>197</index>
        <index>195</index>
        <index>196</index>
        <index>197</index>
        <index>195</index>
        <index>197</index>
        <index>44</index>
        <index>42</index>
        <index>199</index>
        <index>198</index>
        <index>199</index>
        <index>43</index>
        <index>200</index>
        <index>198</index>
        <index>199</index>
        <index>200</index>
        <index>198</index>
        <index>200</index>
        <index>44</index>
        <index>42</index>
        <index>202</index>
        <index>201</index>
        <index>202</index>
        <index>44</index>
        <index>203</index>
        <index>201</index>
        <index>202</index>
        <index>203</index>
        <index>201</index>
        <index>203</index>
        <index>11</index>
        <index>3</index>
        <index>205</index>
        <index>204</index>
        <index>205</index>
        <index>46</index>
        <index>206</index>
        <index>204</index>
        <index>205</index>
        <index>206</index>
        <index>204</index>
        <index>206</index>
        <index>45</index>
        <index>46</index>
        <index>208</index>
        <index>207</index>
        <index>208</index>
        <index>11</index>
        <index>209</index>
        <index>207</index>
        <index>208</index>
        <index>209</index>
        <index>207</index>
        <index>209</index>
        <index>47</index>
        <index>45</index>
        <index>211</index>
        <index>210</index>
        <index>211</index>
        <index>46</index>
        <index>212</index>
        <index>210</index>
        <index>211</index>
        <index>212</index>
        <index>210</index>
        <index>212</index>
        <index>47</index>
        <index>45</index>
        <index>214</index>
        <index>213</index>
        <index>214</index>
        <index>47</index>
        <index>215</index>
        <index>213</index>
        <index>214</index>
        <index>215</index>
        <index>213</index>
        <index>215</index>
        <index>9</index>
        <index>4</index>
        <index>217</index>
        <index>216</index>
        <index>217</index>
        <index>49</index>
        <index>218</index>
        <index>216</index>
        <index>217</index>
        <index>218</index>
        <index>216</index>
        <index>218</index>
        <index>48</index>
        <index>49</index>
        <index>220</index>
        <index>219</index>
        <index>220</index>
        <index>2</index>
        <index>221</index>
        <index>219</index>
        <index>220</index>
        <index>221</index>
        <index>219</index>
        <index>221</index>
        <index>50</index>
        <index>48</index>
        <index>223</index>
        <index>222</index>
        <index>223</index>
        <index>49</index>
        <index>224</index>
        <index>222</index>
        <index>223</index>
        <index>224</index>
        <index>222</index>
        <index>224</index>
        <index>50</index>
        <index>48</index>
        <index>226</index>
        <index>225</index>
        <index>226</index>
        <index>50</index>
        <index>227</index>
        <index>225</index>
        <index>226</index>
        <index>227</index>
        <index>225</index>
        <index>227</index>
        <index>0</index>
        <index>5</index>
        <index>229</index>
        <index>228</index>
        <index>229</index>
        <index>52</index>
        <index>230</index>
        <index>228</index>
        <index>229</index>
        <index>230</index>
        <index>228</index>
        <index>230</index>
        <index>51</index>
        <index>52</index>
        <index>232</index>
        <index>231</index>
        <index>232</index>
        <index>0</index>
        <index>233</index>
        <index>231</index>
        <index>232</index>
        <index>233</index>
        <index>231</index>
        <index>233</index>
        <index>53</index>
        <index>51</index>
        <index>235</index>
        <index>234</index>
        <index>235</index>
        <index>52</index>
        <index>236</index>
        <index>234</index>
        <index>235</index>
        <index>236</index>
        <index>234</index>
        <index>236</index>
        <index>53</index>
        <index>51</index>
        <index>238</index>
        <index>237</index>
        <index>238</index>
        <index>53</index>
        <index>239</index>
        <index>237</index>
        <index>238</index>
        <index>239</index>
        <index>237</index>
        <index>239</index>
        <index>2</index>
        <index>6</index>
        <index>241</index>
        <index>240</index>
        <index>241</index>
        <index>55</index>
        <index>242</index>
        <index>240</index>
        <index>241</index>
        <index>242</index>
        <index>240</index>
        <index>242</index>
        <index>54</index>
        <index>55</index>
        <index>244</index>
        <index>243</index>
        <index>244</index>
        <index>1</index>
        <index>245</index>
        <index>243</index>
        <index>244</index>
        <index>245</index>
        <index>243</index>
        <index>245</index>
        <index>56</index>
        <index>54</index>
        <index>247</index>
        <index>246</index>
        <index>247</index>
        <index>55</index>
        <index>248</index>
        <index>246</index>
        <index>247</index>
        <index>248</index>
        <index>246</index>
        <index>248</index>
        <index>56</index>
        <index>54</index>
        <index>250</index>
        <index>249</index>
        <index>250</index>
        <index>56</index>
        <index>251</index>
        <index>249</index>
        <index>250</index>
        <index>251</index>
        <index>249</index>
        <index>251</index>
        <index>3</index>
        <index>7</index>
        <index>253</index>
        <index>252</index>
        <index>253</index>
        <index>58</index>
        <index>254</index>
        <index>252</index>
        <index>253</index>
        <index>254</index>
        <index>252</index>
        <index>254</index>
        <index>57</index>
        <index>58</index>
        <index>256</index>
        <index>255</index>
        <index>256</index>
        <index>3</index>
        <index>257</index>
        <index>255</index>
        <index>256</index>
        <index>257</index>
        <index>255</index>
        <index>257</index>
        <index>59</index>
        <index>57</index>
        <index>259</index>
        <index>258</index>
        <index>259</index>
        <index>58</index>
        <index>260</index>
        <index>258</index>
        <index>259</index>
        <index>260</index>
        <index>258</index>
        <index>260</index>
        <index>59</index>
        <index>57</index>
        <index>262</index>
        <index>261</index>
        <index>262</index>
        <index>59</index>
        <index>263</index>
        <index>261</index>
        <index>262</index>
        <index>263</index>
        <index>261</index>
        <index>263</index>
        <index>1</index>
        <index>8</index>
        <index>265</index>
        <index>264</index>
        <index>265</index>
        <index>61</index>
        <index>266</index>
        <index>264</index>
        <index>265</index>
        <index>266</index>
        <index>264</index>
        <index>266</index>
        <index>60</index>
        <index>61</index>
        <index>268</index>
        <index>267</index>
        <index>268</index>
        <index>6</index>
        <index>269</index>
        <index>267</index>
        <index>268</index>
        <index>269</index>
        <index>267</index>
        <index>269</index>
        <index>62</index>
        <index>60</index>
        <index>271</index>
        <index>270</index>
        <index>271</index>
        <index>61</index>
        <index>272</index>
        <index>270</index>
        <index>271</index>
        <index>272</index>
        <index>270</index>
        <index>272</index>
        <index>62</index>
        <index>60</index>
        <index>274</index>
        <index>273</index>
        <index>274</index>
        <index>62</index>
        <index>275</index>
        <index>273</index>
        <index>274</index>
        <index>275</index>
        <index>273</index>
        <index>275</index>
        <index>4</index>
        <index>9</index>
        <index>277</index>
        <index>276</index>
        <index>277</index>
        <index>64</index>
        <index>278</index>
        <index>276</index>
        <index>277</index>
        <index>278</index>
        <index>276</index>
        <index>278</index>
        <index>63</index>
        <index>64</index>
        <index>280</index>
        <index>279</index>
        <index>280</index>
        <index>4</index>
        <index>281</index>
        <index>279</index>
        <index>280</index>
        <index>281</index>
        <index>279</index>
        <index>281</index>
        <index>65</index>
        <index>63</index>
        <index>283</index>
        <index>282</index>
        <index>283</index>
        <index>64</index>
        <index>284</index>
        <index>282</index>
        <index>283</index>
        <index>284</index>
        <index>282</index>
        <index>284</index>
        <index>65</index>
        <index>63</index>
        <index>286</index>
        <index>285</index>
        <index>286</index>
        <index>65</index>
        <index>287</index>
        <index>285</index>
        <index>286</index>
        <index>287</index>
        <index>285</index>
        <index>287</index>
        <index>6</index>
        <index>10</index>
        <index>289</index>
        <index>288</index>
        <index>289</index>
        <index>67</index>
        <index>290</index>
        <index>288</index>
        <index>289</index>
        <index>290</index>
        <index>288</index>
        <index>290</index>
        <index>66</index>
        <index>67</index>
        <index>292</index>
        <index>291</index>
        <index>292</index>
        <index>5</index>
        <index>293</index>
        <index>291</index>
        <index>292</index>
        <index>293</index>
        <index>291</index>
        <index>293</index>
        <index>68</index>
        <index>66</index>
        <index>295</index>
        <index>294</index>
        <index>295</index>
        <index>67</index>
        <index>296</index>
        <index>294</index>
        <index>295</index>
        <index>296</index>
        <index>294</index>
        <index>296</index>
        <index>68</index>
        <index>66</index>
        <index>298</index>
        <index>297</index>
        <index>298</index>
        <index>68</index>
        <index>299</index>
        <index>297</index>
        <index>298</index>
        <index>299</index>
        <index>297</index>
        <index>299</index>
        <index>7</index>
        <index>11</index>
        <index>301</index>
        <index>300</index>
        <index>301</index>
        <index>70</index>
        <index>302</index>
        <index>300</index>
        <index>301</index>
        <index>302</index>
        <index>300</index>
        <index>302</index>
        <index>69</index>
        <index>70</index>
        <index>304</index>
        <index>303</index>
        <index>304</index>
        <index>7</index>
        <index>305</index>
        <index>303</index>
        <index>304</index>
        <index>305</index>
        <index>303</index>
        <index>305</index>
        <index>71</index>
        <index>69</index>
        <index>307</index>
        <index>306</index>
        <index>307</index>
        <index>70</index>
        <index>308</index>
        <index>306</index>
        <index>307</index>
        <index>308</index>
        <index>306</index>
        <index>308</index>
        <index>71</index>
        <index>69</index>
        <index>310</index>
        <index>309</index>
        <index>310</index>
        <index>71</index>
        <index>311</index>
        <index>309</index>
        <index>310</index>
        <index>311</index>
        <index>309</index>
        <index>311</index>
        <index>5</index>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.PolygonNode">
      <name>Polygon</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="0.0" y="0.0" z="0.0"/>
      <localScale x="1.0" y="1.0" z="1.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.Polygon">
        <numVerices>4</numVerices>
        <vertex x="0.0" y="0.0" z="10.0"/>
        <vertex x="1.0" y="1.0" z="10.0"/>
        <vertex x="2.0" y="1.0" z="10.0"/>
        <vertex x="3.0" y="-1.0" z="10.0"/>
        <stripCounts size="1">
          <value>4</value>
        </stripCounts>
        <contourCounts size="1">
          <value>1</value>
        </contourCounts>
        <cloded>false</cloded>
      </geometry>
    </element>
    <element class="net.untoldwind.moredread.model.scene.GridNode">
      <name>Grid</name>
      <modelColor r="1.0" g="0.0" b="0.0" a="1.0"/>
      <localTranslation x="0.0" y="0.0" z="20.0"/>
      <localScale x="1.0" y="1.0" z="1.0"/>
      <localRotation x="0.0" y="0.0" z="0.0" w="1.0"/>
      <geometry class="net.untoldwind.moredread.model.mesh.Grid">
        <numVerices>100</numVerices>
        <vertex x="0.0" y="0.0" z="0.0"/>
        <vertex x="0.0" y="1.0" z="0.0"/>
        <vertex x="0.0" y="2.0" z="0.0"/>
        <vertex x="0.0" y="3.0" z="0.0"/>
        <vertex x="0.0" y="4.0" z="0.0"/>
        <vertex x="0.0" y="5.0" z="0.0"/>
        <vertex x="0.0" y="6.0" z="0.0"/>
        <vertex x="0.0" y="7.0" z="0.0"/>
        <vertex x="0.0" y="8.0" z="0.0"/>
        <vertex x="0.0" y="9.0" z="0.0"/>
        <vertex x="1.0" y="0.0" z="0.0"/>
        <vertex x="1.0" y="1.0" z="0.0"/>
        <vertex x="1.0" y="2.0" z="0.0"/>
        <vertex x="1.0" y="3.0" z="0.0"/>
        <vertex x="1.0" y="4.0" z="0.0"/>
        <vertex x="1.0" y="5.0" z="0.0"/>
        <vertex x="1.0" y="6.0" z="0.0"/>
        <vertex x="1.0" y="7.0" z="0.0"/>
        <vertex x="1.0" y="8.0" z="0.0"/>
        <vertex x="1.0" y="9.0" z="0.0"/>
        <vertex x="2.0" y="0.0" z="0.0"/>
        <vertex x="2.0" y="1.0" z="0.0"/>
        <vertex x="2.0" y="2.0" z="0.0"/>
        <vertex x="2.0" y="3.0" z="0.0"/>
        <vertex x="2.0" y="4.0" z="0.0"/>
        <vertex x="2.0" y="5.0" z="0.0"/>
        <vertex x="2.0" y="6.0" z="0.0"/>
        <vertex x="2.0" y="7.0" z="0.0"/>
        <vertex x="2.0" y="8.0" z="0.0"/>
        <vertex x="2.0" y="9.0" z="0.0"/>
        <vertex x="3.0" y="0.0" z="0.0"/>
        <vertex x="3.0" y="1.0" z="0.0"/>
        <vertex x="3.0" y="2.0" z="0.0"/>
        <vertex x="3.0" y="3.0" z="0.0"/>
        <vertex x="3.0" y="4.0" z="0.0"/>
        <vertex x="3.0" y="5.0" z="0.0"/>
        <vertex x="3.0" y="6.0" z="0.0"/>
        <vertex x="3.0" y="7.0" z="0.0"/>
        <vertex x="3.0" y="8.0" z="0.0"/>
        <vertex x="3.0" y="9.0" z="0.0"/>
        <vertex x="4.0" y="0.0" z="0.0"/>
        <vertex x="4.0" y="1.0" z="0.0"/>
        <vertex x="4.0" y="2.0" z="0.0"/>
        <vertex x="4.0" y="3.0" z="0.0"/>
        <vertex x="4.0" y="4.0" z="0.0"/>
        <vertex x="4.0" y="5.0" z="0.0"/>
        <vertex x="4.0" y="6.0" z="0.0"/>
        <vertex x="4.0" y="7.0" z="0.0"/>
        <vertex x="4.0" y="8.0" z="0.0"/>
        <vertex x="4.0" y="9.0" z="0.0"/>
        <vertex x="5.0" y="0.0" z="0.0"/>
        <vertex x="5.0" y="1.0" z="0.0"/>
        <vertex x="5.0" y="2.0" z="0.0"/>
        <vertex x="5.0" y="3.0" z="0.0"/>
        <vertex x="5.0" y="4.0" z="0.0"/>
        <vertex x="5.0" y="5.0" z="0.0"/>
        <vertex x="5.0" y="6.0" z="0.0"/>
        <vertex x="5.0" y="7.0" z="0.0"/>
        <vertex x="5.0" y="8.0" z="0.0"/>
        <vertex x="5.0" y="9.0" z="0.0"/>
        <vertex x="6.0" y="0.0" z="0.0"/>
        <vertex x="6.0" y="1.0" z="0.0"/>
        <vertex x="6.0" y="2.0" z="0.0"/>
        <vertex x="6.0" y="3.0" z="0.0"/>
        <vertex x="6.0" y="4.0" z="0.0"/>
        <vertex x="6.0" y="5.0" z="0.0"/>
        <vertex x="6.0" y="6.0" z="0.0"/>
        <vertex x="6.0" y="7.0" z="0.0"/>
        <vertex x="6.0" y="8.0" z="0.0"/>
        <vertex x="6.0" y="9.0" z="0.0"/>
        <vertex x="7.0" y="0.0" z="0.0"/>
        <vertex x="7.0" y="1.0" z="0.0"/>
        <vertex x="7.0" y="2.0" z="0.0"/>
        <vertex x="7.0" y="3.0" z="0.0"/>
        <vertex x="7.0" y="4.0" z="0.0"/>
        <vertex x="7.0" y="5.0" z="0.0"/>
        <vertex x="7.0" y="6.0" z="0.0"/>
        <vertex x="7.0" y="7.0" z="0.0"/>
        <vertex x="7.0" y="8.0" z="0.0"/>
        <vertex x="7.0" y="9.0" z="0.0"/>
        <vertex x="8.0" y="0.0" z="0.0"/>
        <vertex x="8.0" y="1.0" z="0.0"/>
        <vertex x="8.0" y="2.0" z="0.0"/>
        <vertex x="8.0" y="3.0" z="0.0"/>
        <vertex x="8.0" y="4.0" z="0.0"/>
        <vertex x="8.0" y="5.0" z="0.0"/>
        <vertex x="8.0" y="6.0" z="0.0"/>
        <vertex x="8.0" y="7.0" z="0.0"/>
        <vertex x="8.0" y="8.0" z="0.0"/>
        <vertex x="8.0" y="9.0" z="0.0"/>
        <vertex x="9.0" y="0.0" z="0.0"/>
        <vertex x="9.0" y="1.0" z="0.0"/>
        <vertex x="9.0" y="2.0" z="0.0"/>
        <vertex x="9.0" y="3.0" z="0.0"/>
        <vertex x="9.0" y="4.0" z="0.0"/>
        <vertex x="9.0" y="5.0" z="0.0"/>
        <vertex x="9.0" y="6.0" z="0.0"/>
        <vertex x="9.0" y="7.0" z="0.0"/>
        <vertex x="9.0" y="8.0" z="0.0"/>
        <vertex x="9.0" y="9.0" z="0.0"/>
        <numEdges>180</numEdges>
        <idx1>61</idx1>
        <idx2>51</idx2>
        <idx1>86</idx1>
        <idx2>76</idx2>
        <idx1>1</idx1>
        <idx2>0</idx2>
        <idx1>44</idx1>
        <idx2>34</idx2>
        <idx1>18</idx1>
        <idx2>17</idx2>
        <idx1>77</idx1>
        <idx2>76</idx2>
        <idx1>10</idx1>
        <idx2>0</idx2>
        <idx1>52</idx1>
        <idx2>51</idx2>
        <idx1>94</idx1>
        <idx2>93</idx2>
        <idx1>27</idx1>
        <idx2>17</idx2>
        <idx1>35</idx1>
        <idx2>34</idx2>
        <idx1>69</idx1>
        <idx2>59</idx2>
        <idx1>78</idx1>
        <idx2>68</idx2>
        <idx1>9</idx1>
        <idx2>8</idx2>
        <idx1>52</idx1>
        <idx2>42</idx2>
        <idx1>95</idx1>
        <idx2>85</idx2>
        <idx1>26</idx1>
        <idx2>25</idx2>
        <idx1>69</idx1>
        <idx2>68</idx2>
        <idx1>18</idx1>
        <idx2>8</idx2>
        <idx1>86</idx1>
        <idx2>85</idx2>
        <idx1>35</idx1>
        <idx2>25</idx2>
        <idx1>43</idx1>
        <idx2>42</idx2>
        <idx1>60</idx1>
        <idx2>50</idx2>
        <idx1>87</idx1>
        <idx2>77</idx2>
        <idx1>2</idx1>
        <idx2>1</idx2>
        <idx1>45</idx1>
        <idx2>35</idx2>
        <idx1>17</idx1>
        <idx2>16</idx2>
        <idx1>78</idx1>
        <idx2>77</idx2>
        <idx1>11</idx1>
        <idx2>1</idx2>
        <idx1>51</idx1>
        <idx2>50</idx2>
        <idx1>93</idx1>
        <idx2>92</idx2>
        <idx1>26</idx1>
        <idx2>16</idx2>
        <idx1>36</idx1>
        <idx2>35</idx2>
        <idx1>68</idx1>
        <idx2>58</idx2>
        <idx1>79</idx1>
        <idx2>69</idx2>
        <idx1>53</idx1>
        <idx2>43</idx2>
        <idx1>94</idx1>
        <idx2>84</idx2>
        <idx1>25</idx1>
        <idx2>24</idx2>
        <idx1>19</idx1>
        <idx2>9</idx2>
        <idx1>59</idx1>
        <idx2>58</idx2>
        <idx1>85</idx1>
        <idx2>84</idx2>
        <idx1>34</idx1>
        <idx2>24</idx2>
        <idx1>44</idx1>
        <idx2>43</idx2>
        <idx1>42</idx1>
        <idx2>32</idx2>
        <idx1>59</idx1>
        <idx2>49</idx2>
        <idx1>88</idx1>
        <idx2>78</idx2>
        <idx1>3</idx1>
        <idx2>2</idx2>
        <idx1>96</idx1>
        <idx2>95</idx2>
        <idx1>29</idx1>
        <idx2>19</idx2>
        <idx1>33</idx1>
        <idx2>32</idx2>
        <idx1>79</idx1>
        <idx2>78</idx2>
        <idx1>12</idx1>
        <idx2>2</idx2>
        <idx1>50</idx1>
        <idx2>40</idx2>
        <idx1>97</idx1>
        <idx2>87</idx2>
        <idx1>28</idx1>
        <idx2>27</idx2>
        <idx1>67</idx1>
        <idx2>57</idx2>
        <idx1>80</idx1>
        <idx2>70</idx2>
        <idx1>11</idx1>
        <idx2>10</idx2>
        <idx1>88</idx1>
        <idx2>87</idx2>
        <idx1>37</idx1>
        <idx2>27</idx2>
        <idx1>41</idx1>
        <idx2>40</idx2>
        <idx1>71</idx1>
        <idx2>70</idx2>
        <idx1>20</idx1>
        <idx2>10</idx2>
        <idx1>58</idx1>
        <idx2>57</idx2>
        <idx1>43</idx1>
        <idx2>33</idx2>
        <idx1>19</idx1>
        <idx2>18</idx2>
        <idx1>58</idx1>
        <idx2>48</idx2>
        <idx1>89</idx1>
        <idx2>79</idx2>
        <idx1>4</idx1>
        <idx2>3</idx2>
        <idx1>95</idx1>
        <idx2>94</idx2>
        <idx1>28</idx1>
        <idx2>18</idx2>
        <idx1>34</idx1>
        <idx2>33</idx2>
        <idx1>13</idx1>
        <idx2>3</idx2>
        <idx1>49</idx1>
        <idx2>48</idx2>
        <idx1>51</idx1>
        <idx2>41</idx2>
        <idx1>96</idx1>
        <idx2>86</idx2>
        <idx1>27</idx1>
        <idx2>26</idx2>
        <idx1>66</idx1>
        <idx2>56</idx2>
        <idx1>81</idx1>
        <idx2>71</idx2>
        <idx1>12</idx1>
        <idx2>11</idx2>
        <idx1>87</idx1>
        <idx2>86</idx2>
        <idx1>36</idx1>
        <idx2>26</idx2>
        <idx1>42</idx1>
        <idx2>41</idx2>
        <idx1>72</idx1>
        <idx2>71</idx2>
        <idx1>21</idx1>
        <idx2>11</idx2>
        <idx1>57</idx1>
        <idx2>56</idx2>
        <idx1>56</idx1>
        <idx2>55</idx2>
        <idx1>73</idx1>
        <idx2>72</idx2>
        <idx1>14</idx1>
        <idx2>4</idx2>
        <idx1>39</idx1>
        <idx2>38</idx2>
        <idx1>31</idx1>
        <idx2>21</idx2>
        <idx1>82</idx1>
        <idx2>72</idx2>
        <idx1>5</idx1>
        <idx2>4</idx2>
        <idx1>65</idx1>
        <idx2>55</idx2>
        <idx1>99</idx1>
        <idx2>89</idx2>
        <idx1>22</idx1>
        <idx2>21</idx2>
        <idx1>48</idx1>
        <idx2>38</idx2>
        <idx1>64</idx1>
        <idx2>63</idx2>
        <idx1>65</idx1>
        <idx2>64</idx2>
        <idx1>22</idx1>
        <idx2>12</idx2>
        <idx1>47</idx1>
        <idx2>46</idx2>
        <idx1>82</idx1>
        <idx2>81</idx2>
        <idx1>39</idx1>
        <idx2>29</idx2>
        <idx1>74</idx1>
        <idx2>64</idx2>
        <idx1>13</idx1>
        <idx2>12</idx2>
        <idx1>73</idx1>
        <idx2>63</idx2>
        <idx1>91</idx1>
        <idx2>81</idx2>
        <idx1>99</idx1>
        <idx2>98</idx2>
        <idx1>56</idx1>
        <idx2>46</idx2>
        <idx1>55</idx1>
        <idx2>54</idx2>
        <idx1>74</idx1>
        <idx2>73</idx2>
        <idx1>15</idx1>
        <idx2>5</idx2>
        <idx1>89</idx1>
        <idx2>88</idx2>
        <idx1>30</idx1>
        <idx2>20</idx2>
        <idx1>83</idx1>
        <idx2>73</idx2>
        <idx1>6</idx1>
        <idx2>5</idx2>
        <idx1>64</idx1>
        <idx2>54</idx2>
        <idx1>98</idx1>
        <idx2>88</idx2>
        <idx1>21</idx1>
        <idx2>20</idx2>
        <idx1>49</idx1>
        <idx2>39</idx2>
        <idx1>63</idx1>
        <idx2>62</idx2>
        <idx1>66</idx1>
        <idx2>65</idx2>
        <idx1>23</idx1>
        <idx2>13</idx2>
        <idx1>48</idx1>
        <idx2>47</idx2>
        <idx1>81</idx1>
        <idx2>80</idx2>
        <idx1>38</idx1>
        <idx2>28</idx2>
        <idx1>75</idx1>
        <idx2>65</idx2>
        <idx1>14</idx1>
        <idx2>13</idx2>
        <idx1>72</idx1>
        <idx2>62</idx2>
        <idx1>90</idx1>
        <idx2>80</idx2>
        <idx1>29</idx1>
        <idx2>28</idx2>
        <idx1>57</idx1>
        <idx2>47</idx2>
        <idx1>37</idx1>
        <idx2>36</idx2>
        <idx1>92</idx1>
        <idx2>91</idx2>
        <idx1>33</idx1>
        <idx2>23</idx2>
        <idx1>54</idx1>
        <idx2>53</idx2>
        <idx1>75</idx1>
        <idx2>74</idx2>
        <idx1>16</idx1>
        <idx2>6</idx2>
        <idx1>24</idx1>
        <idx2>23</idx2>
        <idx1>46</idx1>
        <idx2>36</idx2>
        <idx1>84</idx1>
        <idx2>74</idx2>
        <idx1>7</idx1>
        <idx2>6</idx2>
        <idx1>63</idx1>
        <idx2>53</idx2>
        <idx1>45</idx1>
        <idx2>44</idx2>
        <idx1>84</idx1>
        <idx2>83</idx2>
        <idx1>41</idx1>
        <idx2>31</idx2>
        <idx1>62</idx1>
        <idx2>61</idx2>
        <idx1>67</idx1>
        <idx2>66</idx2>
        <idx1>24</idx1>
        <idx2>14</idx2>
        <idx1>93</idx1>
        <idx2>83</idx2>
        <idx1>32</idx1>
        <idx2>31</idx2>
        <idx1>97</idx1>
        <idx2>96</idx2>
        <idx1>54</idx1>
        <idx2>44</idx2>
        <idx1>76</idx1>
        <idx2>66</idx2>
        <idx1>15</idx1>
        <idx2>14</idx2>
        <idx1>71</idx1>
        <idx2>61</idx2>
        <idx1>38</idx1>
        <idx2>37</idx2>
        <idx1>91</idx1>
        <idx2>90</idx2>
        <idx1>32</idx1>
        <idx2>22</idx2>
        <idx1>53</idx1>
        <idx2>52</idx2>
        <idx1>76</idx1>
        <idx2>75</idx2>
        <idx1>17</idx1>
        <idx2>7</idx2>
        <idx1>23</idx1>
        <idx2>22</idx2>
        <idx1>47</idx1>
        <idx2>37</idx2>
        <idx1>85</idx1>
        <idx2>75</idx2>
        <idx1>8</idx1>
        <idx2>7</idx2>
        <idx1>62</idx1>
        <idx2>52</idx2>
        <idx1>46</idx1>
        <idx2>45</idx2>
        <idx1>83</idx1>
        <idx2>82</idx2>
        <idx1>40</idx1>
        <idx2>30</idx2>
        <idx1>61</idx1>
        <idx2>60</idx2>
        <idx1>68</idx1>
        <idx2>67</idx2>
        <idx1>25</idx1>
        <idx2>15</idx2>
        <idx1>92</idx1>
        <idx2>82</idx2>
        <idx1>31</idx1>
        <idx2>30</idx2>
        <idx1>98</idx1>
        <idx2>97</idx2>
        <idx1>55</idx1>
        <idx2>45</idx2>
        <idx1>77</idx1>
        <idx2>67</idx2>
        <idx1>16</idx1>
        <idx2>15</idx2>
        <idx1>70</idx1>
        <idx2>60</idx2>
      </geometry>
    </element>
  </children>
</scene>
